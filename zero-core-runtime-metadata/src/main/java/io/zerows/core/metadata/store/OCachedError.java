package io.zerows.core.metadata.store;

import io.vertx.core.json.JsonObject;
import io.zerows.core.metadata.zdk.running.OCache;

/**
 * @author lang : 2024-04-17
 */
public class OCachedError implements OCache<JsonObject> {
    @Override
    public JsonObject get() {
        return null;
    }

    @Override
    public OCache<JsonObject> add(final JsonObject entries) {
        return null;
    }

    @Override
    public OCache<JsonObject> remove(final JsonObject entries) {
        return null;
    }
}
