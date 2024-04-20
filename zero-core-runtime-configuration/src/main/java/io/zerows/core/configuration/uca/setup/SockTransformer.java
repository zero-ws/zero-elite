package io.zerows.core.configuration.uca.setup;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.vertx.up.fn.Fn;
import io.vertx.up.util.Ut;
import io.zerows.core.configuration.atom.option.SockOptions;
import io.zerows.core.configuration.zdk.Transformer;

/**
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public class SockTransformer implements Transformer<SockOptions> {

    @Override
    public SockOptions transform(final JsonObject config) {
        return Fn.runOr(null == config, this.tracker(), SockOptions::new, () -> {
            /*
             * websocket:       ( SockOptions )
             * config:          ( HttpServerOptions )
             */
            final JsonObject websockJ = Ut.valueJObject(config, KName.WEB_SOCKET);
            final SockOptions options = Ut.deserialize(websockJ, SockOptions.class);

            /*
             * Bind the HttpServerOptions to SockOptions for future usage
             */
            final JsonObject optionJ = Ut.valueJObject(config, KName.CONFIG);
            final HttpServerOptions serverOptions = new HttpServerOptions(optionJ);
            options.options(serverOptions);
            return options;
        });
    }
}
