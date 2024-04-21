package io.zerows.core.metadata.osgi.spi;

import io.horizon.spi.HorizonIo;
import io.horizon.uca.log.internal.Log4JAnnal;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.zerows.core.metadata.store.OCacheFailure;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author lang : 2023/4/28
 */
public class HorizonIoLoad implements HorizonIo {
    private final OCache<JsonObject> cache;

    public HorizonIoLoad() {
        final Bundle bundle = FrameworkUtil.getBundle(this.getClass());
        this.cache = OCacheFailure.of(bundle);
    }

    @Override
    public JsonObject ofError() {
        return this.cache.get(KName.ERROR);
    }

    @Override
    public JsonObject ofFailure() {
        return this.cache.get(KName.INFO);
    }

    @Override
    public Class<?> ofLogger() {
        return Log4JAnnal.class;
    }
}
