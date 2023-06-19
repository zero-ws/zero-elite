package io.vertx.up.util;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author lang : 2023-06-19
 */
@SuppressWarnings("all")
class _Jackson extends _It {

    /*
     * Serialization method operation method here.
     * 1) serialize / serializeJson
     * 2) deserialize
     *
     * Object converting here of serialization
     */
    public static <T, R extends Iterable> R serializeJson(final T t) {
        return Jackson.serializeJson(t, false);
    }

    public static <T> T deserialize(final JsonObject value, final Class<T> type) {
        return Jackson.deserialize(value, type, true);
    }

    public static <T, R extends Iterable> R serializeJson(final T t, final boolean isSmart) {
        return Jackson.serializeJson(t, isSmart);
    }

    public static <T> T deserialize(final JsonObject value, final Class<T> type, boolean isSmart) {
        return Jackson.deserialize(value, type, isSmart);
    }

    public static <T> T deserialize(final JsonArray value, final Class<T> type, boolean isSmart) {
        return Jackson.deserialize(value, type, isSmart);
    }

    public static <T> T deserialize(final String value, final Class<T> clazz, boolean isSmart) {
        return Jackson.deserialize(value, clazz, isSmart);
    }
}
