package io.zerows.core.configuration.store;

import io.zerows.core.configuration.atom.NodeNetwork;
import io.zerows.core.configuration.atom.NodeVertx;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-20
 */
public interface OCacheNode extends OCache<NodeVertx> {

    static OCacheNode of() {
        return OCacheNodeAmbiguity.of();
    }

    static OCacheNode of(final Bundle bundle) {
        return OCacheNodeAmbiguity.of(bundle);
    }

    NodeNetwork network();
}
