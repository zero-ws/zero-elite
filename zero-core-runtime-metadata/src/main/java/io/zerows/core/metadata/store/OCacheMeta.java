package io.zerows.core.metadata.store;

import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

import java.util.Set;

/**
 * @author lang : 2024-04-22
 */
public interface OCacheMeta extends OCache<Set<OCache<?>>> {

    static OCacheMeta of(final Bundle bundle) {
        return OCacheMetaAmbiguity.of(bundle);
    }
}
