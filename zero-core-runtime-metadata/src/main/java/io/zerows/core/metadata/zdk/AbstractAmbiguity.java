package io.zerows.core.metadata.zdk;

import org.osgi.framework.Bundle;

/**
 * 带有二义性的新组件，用于支持二选一的环境处理，其中包括
 * <pre><code>
 *     1. {@link Bundle} == null：OSGI 环境
 *     2. {@link Bundle} != null：OSGI 环境
 * </code></pre>
 * 其中此处的 Osgi 为 OSGI 环境下的 Osgi 对象，所有服务作用于此对象所需的基类
 *
 * @author lang : 2024-04-17
 */
public abstract class AbstractAmbiguity {

    protected final Bundle bundle;

    protected AbstractAmbiguity(final Bundle bundle) {
        this.bundle = bundle;
    }

    protected AbstractAmbiguity() {
        this(null);
    }

    protected String key(final boolean version) {
        if (version) {
            return this.bundle.getSymbolicName() + "/" + this.bundle.getVersion().getQualifier();
        } else {
            return this.bundle.getSymbolicName();
        }
    }
}
