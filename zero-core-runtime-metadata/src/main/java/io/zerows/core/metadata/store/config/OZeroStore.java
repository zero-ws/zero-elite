package io.zerows.core.metadata.store.config;

import io.horizon.uca.log.internal.Log4JAnnal;
import io.macrocosm.specification.config.HConfig;
import io.macrocosm.specification.config.HSetting;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.configure.YmlCore;
import io.vertx.up.util.Ut;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * @author lang : 2023-05-30
 */
public class OZeroStore {
    private static final HSetting SETTING;
    private static final ConcurrentMap<String, Class<?>> INJECTION = new ConcurrentHashMap<>();

    static {
        {
            // 容器部分配置, 对接 OZeroFailure
            final OZeroFailure cache = OZeroFailure.of(null);
            cache.initialize();
            SETTING = cache.setting();
        }
        /*
         * vertx-inject.yml
         */
        final HConfig injectJ = SETTING.infix(YmlCore.inject.__KEY);
        Ut.<String>itJObject(injectJ.options(), (pluginCls, field) -> {
            if (!pluginCls.equals(Log4JAnnal.class.getName())) {
                Ut.Log.configure(OZeroStore.class).info(
                    "The inject config ( node = {0} ) has been detected plugin ( {1} = {2} )",
                    YmlCore.inject.__KEY, field, pluginCls);
            }
            INJECTION.put(field, Ut.clazz(pluginCls));
        });
    }

    public static <T> T option(final String infixKey, final Class<T> clazz, final Supplier<T> supplier) {
        final HConfig config = SETTING.infix(infixKey);
        if (Objects.isNull(config)) {
            return supplier.get();
        } else {
            final JsonObject options = config.options();
            final T created = Ut.deserialize(options, clazz, false);
            return Objects.isNull(created) ? supplier.get() : created;
        }
    }

    public static JsonObject option(final String infixKey) {
        final HConfig config = SETTING.infix(infixKey);
        if (Objects.isNull(config)) {
            return new JsonObject();
        } else {
            return config.options();
        }
    }

    public static boolean is(final String infixKey) {
        return SETTING.hasInfix(infixKey);
    }

    public static Class<?> injection(final String field) {
        return INJECTION.get(field);
    }

    public static ConcurrentMap<String, Class<?>> injection() {
        return INJECTION;
    }

    public static JsonObject containerJ() {
        final HConfig container = SETTING.container();
        return container.options();
    }

    public static JsonObject launcherJ() {
        final HConfig launcher = SETTING.launcher();
        return launcher.options();
    }

    public static HConfig extension(final String extensionKey) {
        return SETTING.extension(extensionKey);
    }

    public static HConfig launcher() {
        return SETTING.launcher();
    }

    public static HConfig container() {
        return SETTING.container();
    }

    /**
     * 注册机制，主要用于 Zero Extension 模块，可提取 {@link HConfig} 数据来构造扩展模块独有的配置
     *
     * @param extensionKey 扩展模块的唯一标识
     * @param config       扩展模块的配置
     */
    public static void register(final String extensionKey, final HConfig config) {
        SETTING.extension(extensionKey, config);
    }
}