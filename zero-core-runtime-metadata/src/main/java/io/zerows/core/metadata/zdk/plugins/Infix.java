package io.zerows.core.metadata.zdk.plugins;

import io.horizon.uca.log.Annal;
import io.vertx.core.json.JsonObject;
import io.vertx.up.fn.Fn;
import io.zerows.core.metadata.exception.boot.ConfigKeyMissingException;
import io.zerows.core.metadata.store.OZeroStore;
import io.zerows.core.metadata.uca.stable.Ruler;

import java.util.function.Function;

public interface Infix {
    static <R> R init(final String key,
                      final Function<JsonObject, R> executor,
                      final Class<?> clazz) {
        final Annal logger = Annal.get(clazz);
        Fn.outBoot(!OZeroStore.is(key), logger, ConfigKeyMissingException.class,
            clazz, key);
        final JsonObject options = OZeroStore.option(key);
        Fn.outBug(() -> Ruler.verify(key, options), logger);
        return executor.apply(options);
    }

    <T> T get();
}
