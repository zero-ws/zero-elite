package io.zerows.core.configuration.store;

import io.zerows.core.configuration.atom.NodeNetwork;
import io.zerows.core.configuration.atom.NodeVertx;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-20
 */
public interface ONodeCache extends OCache<NodeVertx> {

    static ONodeCache of() {
        return ONodeCacheAmbiguity.of();
    }

    static ONodeCache of(final Bundle bundle) {
        return ONodeCacheAmbiguity.of(bundle);
    }

    NodeNetwork network();
}
