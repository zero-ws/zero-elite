package io.zerows.core.facade.cache;

import io.zerows.core.cloud.cache.CStoreCloud;
import io.zerows.core.metadata.cache.CStore;
import io.zerows.core.security.cache.CStoreSecurity;

/**
 * @author lang : 2024-04-18
 */
public interface CRunning extends
    CStore              // zero-core-runtime-metadata
    ,
    CStoreSecurity      // zero-core-region-security
    ,
    CStoreCloud         // zero-core-region-cloud
{
}
