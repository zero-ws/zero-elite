package io.vertx.up.util;

import io.horizon.eon.VPath;
import io.horizon.eon.VString;
import io.horizon.uca.cache.Cc;
import io.horizon.util.HUt;
import io.vertx.core.json.JsonObject;
import io.vertx.up.fn.Fn;
import org.osgi.framework.Bundle;

import java.net.URL;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author lang : 2024-04-17
 */
final class BundleIo {
    // key = filename + bundleId
    private static final Cc<String, JsonObject> CCD_FILE_DEFAULT = Cc.open();
    // key = filename
    private static final Cc<String, JsonObject> CCD_FILE_STORE = Cc.open();

    static JsonObject ioDefault(final String filename, final Bundle bundle) {
        final String filepath = "bundle/" + filename;
        final String cachedKey = Objects.isNull(bundle) ?
            filepath : filepath + VString.SLASH + bundle.getBundleId();
        final JsonObject storeDefault = CCD_FILE_DEFAULT.pick(() -> ioSmart(filepath, bundle), cachedKey);
        return HUt.valueJObject(storeDefault);
    }

    static JsonObject ioCombine(final String filename, final Bundle bundle) {
        final JsonObject defaultJ = ioDefault(filename, bundle);
        final JsonObject configureJ = ioSmart(filename, bundle);
        final JsonObject resultJ = new JsonObject();
        // default <-- configured
        resultJ.mergeIn(defaultJ, true);
        if (Ut.isNotNil(configureJ)) {
            resultJ.mergeIn(configureJ, true);
        }
        return resultJ;
    }

    static JsonObject ioPriority(final String filename, final Bundle bundle) {
        // 提供配置
        final JsonObject configureJ = ioConfigure(filename);
        if (HUt.isNotNil(configureJ)) {
            return configureJ;
        }

        // 默认配置
        final JsonObject defaultJ = ioDefault(filename, bundle);
        return HUt.valueJObject(defaultJ);
    }

    static JsonObject ioConfigure(final String filename) {
        JsonObject storedConfigured = new JsonObject();
        if (Ut.ioExist(filename)) {
            storedConfigured = CCD_FILE_STORE.pick(() -> ioSmart(filename, null), filename);
        } else {
            // 解决 classpath 未指定成功的情况
            final String fileConf = "conf/" + filename;
            if (Ut.ioExist(fileConf)) {
                storedConfigured = CCD_FILE_STORE.pick(() -> ioSmart(fileConf, null), fileConf);
            }
        }
        return Ut.valueJObject(storedConfigured);
    }

    private static JsonObject ioSmart(final String filename, final Bundle bundle) {
        if (HUt.isNil(filename)) {
            return new JsonObject();
        }
        return filename.endsWith(VString.DOT + VPath.SUFFIX.JSON)
            ? ioJObject(filename, bundle) : ioYaml(filename, bundle);
    }

    private static JsonObject ioJObject(final String filename, final Bundle bundle) {
        return ioSafe(filename, bundle, HUt::ioJObject);
    }

    private static JsonObject ioYaml(final String filename, final Bundle bundle) {
        return ioSafe(filename, bundle, HUt::ioYaml);
    }

    private static JsonObject ioSafe(final String filename, final Bundle bundle, final Function<URL, JsonObject> executor) {
        URL url;
        if (Objects.isNull(bundle)) {
            url = Thread.currentThread().getContextClassLoader().getResource(filename);
            if (Objects.isNull(url)) {
                url = BundleIo.class.getResource(filename);
            }
        } else {
            url = bundle.getResource(filename);
        }
        final URL finalUrl = url;
        return Fn.jvmOr(new JsonObject(), () -> {
            if (Objects.isNull(finalUrl)) {
                return new JsonObject();
            }
            return Ut.ioExist(finalUrl) ? executor.apply(finalUrl) : new JsonObject();
        });
    }
}
