package io.vertx.up.uca.options;

import io.vertx.core.json.JsonObject;

public interface Transformer<T> {
    T transform(JsonObject input);
}
