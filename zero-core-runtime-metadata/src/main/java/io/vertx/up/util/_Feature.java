package io.vertx.up.util;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author lang : 2024-04-19
 */
class _Feature extends _Element {
    protected _Feature() {
    }

    @SuppressWarnings("all")
    public static <T> Future<T> future(final T entity) {
        if (entity instanceof Throwable) {
            return Future.failedFuture((Throwable) entity);
        } else {
            return Future.succeededFuture(entity);
        }
    }

    public static <T> Future<T> future() {
        return Future.succeededFuture();
    }

    public static Future<JsonArray> futureA() {
        return Future.succeededFuture(new JsonArray());
    }

    public static Future<JsonObject> futureJ() {
        return Future.succeededFuture(new JsonObject());
    }

    public static Future<Boolean> futureT() {
        return Future.succeededFuture(Boolean.TRUE);
    }

    public static Future<Boolean> futureF() {
        return Future.succeededFuture(Boolean.FALSE);
    }
}
