package io.vertx.up.uca.destine;

import io.vertx.core.json.JsonArray;
import io.vertx.up.atom.shape.KJoin;

/**
 * @author lang : 2023-07-31
 */
class ConflateAIn extends ConflateBase<JsonArray, JsonArray> {
    ConflateAIn(final KJoin joinRef) {
        super(joinRef);
    }

    @Override
    public JsonArray treat(final JsonArray active, final JsonArray assist, final String identifier) {
        return null;
    }

    @Override
    public JsonArray treat(final JsonArray active, final String identifier) {
        return null;
    }
}
