package io.zerows.core.configuration.zdk;

import io.vertx.core.json.JsonObject;

public interface Transformer<T> {
    T transform(JsonObject input);
}
