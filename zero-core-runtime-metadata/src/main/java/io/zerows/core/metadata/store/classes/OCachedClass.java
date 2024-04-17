package io.zerows.core.metadata.store.classes;

import io.horizon.eon.VString;
import io.horizon.uca.cache.Cc;
import io.vertx.up.eon.KMeta;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

import java.util.Objects;
import java.util.Set;

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
public class OCachedClass implements OCache<Set<Class<?>>> {

    private static final Cc<String, OCache<Set<Class<?>>>> CC_SCANNED = Cc.open();
    static String INFO_SCANNED = "Zero system scanned `{0}` classes in total.";
    private final OCache<Set<Class<?>>> scanner;

    private OCachedClass(final Bundle bundle) {
        if (Objects.isNull(bundle)) {
            this.scanner = CC_SCANNED.pick(OCachedWeb::new, KMeta.Component.DEFAULT_SCANNED);
        } else {
            final String key = bundle.getSymbolicName() + VString.SLASH + bundle.getVersion().getQualifier();
            this.scanner = CC_SCANNED.pick(() -> new OCachedOsgi(bundle), key);
        }
    }

    public static OCache<Set<Class<?>>> of(final Bundle bundle) {
        return new OCachedClass(bundle);
    }

    public static OCache<Set<Class<?>>> of() {
        return new OCachedClass(null);
    }

    @Override
    public Set<Class<?>> get() {
        return this.scanner.get();
    }

    @Override
    public OCache<Set<Class<?>>> add(final Set<Class<?>> classes) {
        this.scanner.add(classes);
        return this;
    }

    @Override
    public OCache<Set<Class<?>>> remove(final Set<Class<?>> classes) {
        this.scanner.add(classes);
        return this;
    }
}
