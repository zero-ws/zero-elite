package io.zerows.core.configuration.uca.transformer;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.vertx.up.fn.Fn;
import io.zerows.core.configuration.zdk.Transformer;

import java.util.Objects;

/**
 * @author lang : 2024-04-20
 */
public class HttpServerTransformer implements Transformer<HttpServerOptions> {
    @Override
    public HttpServerOptions transform(final JsonObject input) {
        final JsonObject config = input.getJsonObject(KName.CONFIG, null);
        return Fn.runOr(null == config, this.logger(),
            HttpServerOptions::new,
            () -> {
                assert Objects.nonNull(config) : "`config` is not null";
                return new HttpServerOptions(config);
            });
    }
}
