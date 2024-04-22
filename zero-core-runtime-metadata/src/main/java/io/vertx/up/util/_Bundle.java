package io.vertx.up.util;

import io.vertx.core.json.JsonObject;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author lang : 2024-04-17
 */
class _Bundle extends _Ai {

    public static class Bnd {

        public static void bindCommand(final BundleContext context,
                                       final Object... args) {
            BundleCmd.bindCommand(context, args);
        }

        // OSGI 新接口
        public static <T> T serviceSPI(final Class<T> interfaceCls) {
            final Bundle bundle = FrameworkUtil.getBundle(interfaceCls);
            return BundleSPI.service(interfaceCls, bundle);
        }

        public static <T> T serviceSPI(final Class<T> interfaceCls, final Bundle bundle) {
            return BundleSPI.service(interfaceCls, bundle);
        }

        // OSGI IO 配置专用接口
        public static JsonObject ioDefaultJ(final String filename, final Class<?> clazz) {
            final Bundle bundle = FrameworkUtil.getBundle(clazz);
            return BundleIo.ioDefault(filename, bundle);
        }

        public static JsonObject ioDefaultJ(final String filename, final Bundle bundle) {
            return BundleIo.ioDefault(filename, bundle);
        }

        public static JsonObject ioCombineJ(final String filename, final Class<?> clazz) {
            final Bundle bundle = FrameworkUtil.getBundle(clazz);
            return BundleIo.ioCombine(filename, bundle);
        }

        public static JsonObject ioCombineJ(final String filename, final Bundle bundle) {
            return BundleIo.ioCombine(filename, bundle);
        }

        public static JsonObject ioConfigureJ(final String filename, final Class<?> clazz) {
            final Bundle bundle = FrameworkUtil.getBundle(clazz);
            return BundleIo.ioPriority(filename, bundle);
        }

        public static JsonObject ioConfigureJ(final String filename, final Bundle bundle) {
            return BundleIo.ioPriority(filename, bundle);
        }
    }
}
