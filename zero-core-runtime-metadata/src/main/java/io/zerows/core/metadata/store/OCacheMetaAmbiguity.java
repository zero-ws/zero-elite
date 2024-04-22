package io.zerows.core.metadata.store;

import io.horizon.eon.VString;
import io.horizon.uca.cache.Cc;
import io.zerows.core.metadata.zdk.AbstractAmbiguity;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

import java.util.Set;

/**
 * @author lang : 2024-04-22
 */
class OCacheMetaAmbiguity extends AbstractAmbiguity implements OCacheMeta {
    /**
     * 整体数据结构（全环境）
     * <pre><code>
     *     1. 每个 Bundle 都会有一个 {@link OCacheMeta} 引用
     *        但是最终方法的结构都是 CC_META 的相关数据
     *        bundle-01 -> OCacheMeta-01
     *        bundle-02 -> OCacheMeta-02
     *        ......
     *     2. CC_META 的完整数据结构
     *        bundle-01/version ->  OCache<?> 01
     *                                        - T-01
     *                                        - T-02
     *                              OCache<?> 02
     *                              OCache<?> 03
     * </code></pre>
     */
    private static final Cc<String, OCacheMeta> CC_META = Cc.open();

    private OCacheMetaAmbiguity(final Bundle bundle) {
        super(bundle);
    }

    static OCacheMeta of(final Bundle bundle) {
        final String key = bundle.getSymbolicName() + VString.SLASH + bundle.getVersion().getQualifier();
        return CC_META.pick(() -> new OCacheMetaAmbiguity(bundle), key);
    }

    @Override
    public Set<String> keys() {
        return CC_META.store().keySet();
    }

    @Override
    public Set<OCache<?>> get() {
        final String keyWith = this.key(true);
        return CC_META.store(keyWith).get();
    }

    @Override
    public OCacheMeta add(final Set<OCache<?>> cacheSet) {
        final Set<OCache<?>> stored = this.get();
        stored.addAll(cacheSet);
        return this;
    }

    @Override
    public OCacheMeta remove(final Set<OCache<?>> cacheSet) {
        final Set<OCache<?>> stored = this.get();
        stored.removeAll(cacheSet);
        return this;
    }
}
