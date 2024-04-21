package io.zerows.core.configuration.store;

import io.horizon.eon.VString;
import io.horizon.uca.cache.Cc;
import io.vertx.up.eon.KMeta;
import io.zerows.core.configuration.atom.NodeNetwork;
import io.zerows.core.configuration.atom.NodeVertx;
import io.zerows.core.metadata.zdk.AbstractAmbiguity;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-20
 */
class OCacheNodeAmbiguity extends AbstractAmbiguity implements OCacheNode {

    private static final Cc<String, OCacheNode> CC_SCANNED = Cc.open();

    private final NodeNetwork network = new NodeNetwork();

    private OCacheNodeAmbiguity(final Bundle bundle) {
        super(bundle);
    }

    static OCacheNode of(final Bundle bundle) {
        final String key = bundle.getSymbolicName() + VString.SLASH + bundle.getVersion().getQualifier();
        return CC_SCANNED.pick(() -> new OCacheNodeAmbiguity(bundle), key);
    }

    static OCacheNode of() {
        return CC_SCANNED.pick(() -> new OCacheNodeAmbiguity(null), KMeta.Component.DEFAULT_SCANNED);
    }

    @Override
    public NodeVertx get(final String name) {
        return this.network.get(name);
    }

    @Override
    public NodeNetwork network() {
        return this.network;
    }

    @Override
    public OCache<NodeVertx> add(final NodeVertx nodeVertx) {
        this.network.add(nodeVertx.name(), nodeVertx);
        return this;
    }

    @Override
    public OCache<NodeVertx> remove(final NodeVertx nodeVertx) {
        this.network.remove(nodeVertx.name());
        return this;
    }
}
