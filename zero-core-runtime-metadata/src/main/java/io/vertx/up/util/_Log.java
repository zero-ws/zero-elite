package io.vertx.up.util;

import io.zerows.core.metadata.uca.logging.OLog;

/**
 * @author lang : 2024-04-17
 */
class _Log extends _Load {

    public static class Log {

        public static OLog metadata(final Class<?> clazz) {
            return OLog.of(clazz, "Metadata");
        }

        public static OLog configure(final Class<?> clazz) {
            return OLog.of(clazz, "Callback");
        }

        public static OLog boot(final Class<?> clazz) {
            return OLog.of(clazz, "Boot");
        }
    }
}
