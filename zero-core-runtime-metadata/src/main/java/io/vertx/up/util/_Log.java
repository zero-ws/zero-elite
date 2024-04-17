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

        public static OLog callback(final Class<?> clazz) {
            return OLog.of(clazz, "Callback");
        }

        public static OLog service(final Class<?> clazz) {
            return OLog.of(clazz, "Service");
        }

        public static OLog plugin(final Class<?> clazz) {
            return OLog.of(clazz, "Plugin");
        }

        public static OLog job(final Class<?> clazz) {
            return OLog.of(clazz, "Job");
        }

        public static OLog exception(final Class<?> clazz) {
            return OLog.of(clazz, "Exception");
        }

        public static OLog bundle(final Class<?> clazz) {
            return OLog.of(clazz, "Bundle");
        }
    }
}
