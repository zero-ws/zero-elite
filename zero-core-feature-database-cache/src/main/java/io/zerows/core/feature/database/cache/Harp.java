package io.zerows.core.feature.database.cache;

import io.horizon.uca.log.Annal;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.configure.YmlCore;
import io.zerows.core.feature.database.cache.l1.L1Cache;
import io.zerows.core.metadata.store.OZeroStore;

import java.util.Objects;

/**
 * @author <a href="http://www.origin-x.cn">Lang</a>
 * Life Cycle for cache when processing in
 */
public class Harp {
    private static final Annal LOGGER = Annal.get(Harp.class);
    private static HarpBus BUS_HARP;

    /*
     * First method for initialized and read configuration
     */
    public static void init(final Vertx vertx) {
        if (OZeroStore.is(YmlCore.cache.__KEY)) {
            /*
             * Cache enabled
             */
            final JsonObject config = OZeroStore.option(YmlCore.cache.__KEY);
            // options.getJsonObject(YmlCore.cache.__KEY);
            LOGGER.info("[ Cache ] L1,L2,L3 has been configured: {0}", config);
            BUS_HARP = HarpBus.create(vertx, config);
        }
    }

    public static L1Cache cacheL1() {
        return Objects.isNull(BUS_HARP) ? null : BUS_HARP.cacheL1();
    }
}
