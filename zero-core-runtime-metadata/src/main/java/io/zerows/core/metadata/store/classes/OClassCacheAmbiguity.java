package io.zerows.core.metadata.store.classes;

import io.horizon.eon.VString;
import io.horizon.uca.cache.Cc;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.up.eon.KMeta;
import io.vertx.up.util.Ut;
import io.zerows.core.metadata.uca.scanner.ClassScanner;
import io.zerows.core.metadata.zdk.AbstractAmbiguity;
import io.zerows.core.metadata.zdk.running.OCache;
import io.zerows.core.metadata.zdk.uca.Inquirer;
import org.osgi.framework.Bundle;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * 包扫描器，支持二义性处理，组件数量
 * <pre><code>
 *     1. 非 OSGI 环境，只带单个包扫描组件
 *        内置 static 变量存储了所有扫描的包信息
 *     2. OSGI 环境，每个 Bundle 带有一个包扫描器
 *        内置 static 变量同样存储了所有扫描的包信息
 * </code></pre>
 * Class 对象在底层是唯一的，因此不需要考虑重复添加的问题，也可以直接移出
 *
 * @author lang : 2024-04-17
 */
class OClassCacheAmbiguity extends AbstractAmbiguity implements OClassCache {

    private static final Cc<String, OClassCache> CC_SCANNED = Cc.open();
    private static final String INFO_SCANNED = "Zero system scanned `{0}` classes in total. MetaTree = {1}";

    private final OClassCacheInternal meta;

    private OClassCacheAmbiguity(final Bundle bundle) {
        super(bundle);
        this.meta = OClassCacheInternal.of();
        final ClassLoader loader;
        if (Objects.isNull(bundle)) {
            loader = Thread.currentThread().getContextClassLoader();
        } else {
            loader = bundle.getClass().getClassLoader();
        }
        // Scanner
        final ClassScanner scanner = ClassScanner.of();
        final Set<Class<?>> scanned = scanner.scan(loader);
        Ut.Log.metadata(this.getClass()).info(OClassCacheAmbiguity.INFO_SCANNED,
            String.valueOf(scanned.size()), String.valueOf(this.meta.hashCode()));
        this.meta.add(scanned);
    }

    /**
     * OClassCacheInternal 已经按 Bundle 进行过区分，所以此处不会出现重复的情况
     *
     * @param bundle Bundle
     *
     * @return OCache
     */
    static OClassCache of(final Bundle bundle) {
        final String key = bundle.getSymbolicName() + VString.SLASH + bundle.getVersion().getQualifier();
        return CC_SCANNED.pick(() -> new OClassCacheAmbiguity(bundle), key);
    }

    static OClassCache of() {
        return CC_SCANNED.pick(() -> new OClassCacheAmbiguity(null), KMeta.Component.DEFAULT_SCANNED);
    }

    @Override
    public Set<Class<?>> get() {
        return this.meta.get();
    }

    @Override
    public Set<Class<?>> get(final KMeta.Typed type) {
        return this.meta.get(type);
    }

    @Override
    public OCache<Set<Class<?>>> add(final Set<Class<?>> classes) {
        this.meta.add(classes);
        return this;
    }

    @Override
    public OCache<Set<Class<?>>> remove(final Set<Class<?>> classes) {
        this.meta.remove(classes);
        return this;
    }

    @Override
    public OClassCache remove(final KMeta.Typed type) {
        this.meta.remove(type);
        return this;
    }

    @Override
    public OClassCache remove(final Class<?> clazz) {
        this.meta.remove(clazz);
        return this;
    }

    @Override
    public OClassCache compile(final KMeta.Typed type, final Function<Set<Class<?>>, Set<Class<?>>> compiler) {
        this.meta.compile(type, compiler);
        return this;
    }

    /**
     * 全局类存储池，用于存储当前环境所有的类相关信息，此处的存储考虑几点
     * <pre><code>
     *     提供基础存储哈希表，根据当前环境中是否存在 Bundle 对内容进行提取
     *     1. OSGI 环境
     *        DEFAULT_SCANNED = OClassCacheInternal 的全环境
     *     2. OSGI 环境
     *        Bundle 01 = OClassCacheInternal
     *        Bundle 02 = OClassCacheInternal
     * </code></pre>
     *
     * @author lang : 2024-04-19
     */
    static class OClassCacheInternal {

        private final Set<Class<?>> classSet = new ConcurrentHashSet<>();
        private final ConcurrentMap<KMeta.Typed, Set<Class<?>>> classMap = new ConcurrentHashMap<>();

        private OClassCacheInternal() {
        }

        static OClassCacheInternal of() {
            return new OClassCacheInternal();
        }

        void add(final Set<Class<?>> classes) {
            this.classSet.addAll(classes);
        }

        void addBy(final KMeta.Typed type, final Set<Class<?>> classes) {
            final Set<Class<?>> stored = this.classMap.getOrDefault(type, new ConcurrentHashSet<>());
            stored.addAll(classes);
            this.classMap.put(type, stored);
        }

        /**
         * 特殊方法，可直接绑定
         * {@link Inquirer#scan(Set)} 实现类对应的方法集，最终可保证结果的正确性，且扫描过程
         * 一直在扫描对应的信息，不会重复扫描
         *
         * @param type     类型
         * @param compiler 编译器
         */
        void compile(final KMeta.Typed type, final Function<Set<Class<?>>, Set<Class<?>>> compiler) {
            this.addBy(type, compiler.apply(this.classSet));
        }

        Set<Class<?>> get() {
            return this.classSet;
        }

        Set<Class<?>> get(final KMeta.Typed type) {
            return this.classMap.get(type);
        }

        void remove(final KMeta.Typed type) {
            final Set<Class<?>> removed = this.classMap.remove(type);
            this.classSet.removeAll(removed);
        }

        void remove(final Class<?> clazz) {
            // 基础删除
            this.classSet.remove(clazz);
            // 类型中的删除
            this.removeInternal(clazz);
        }

        void remove(final Set<Class<?>> classes) {
            // 基础删除
            this.classSet.removeAll(classes);
            // 类型中的删除
            classes.forEach(this::removeInternal);
        }

        private void removeInternal(final Class<?> clazz) {
            this.classMap.forEach((type, classStored) -> {
                final Set<Class<?>> storedSet = this.classMap.get(type);
                storedSet.remove(clazz);
                this.classMap.put(type, storedSet);
            });
        }
    }
}
