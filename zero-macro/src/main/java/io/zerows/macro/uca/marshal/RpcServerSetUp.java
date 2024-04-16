package io.zerows.macro.uca.marshal;

import io.horizon.uca.log.Annal;
import io.vertx.core.json.JsonObject;
import io.vertx.up.fn.Fn;
import io.vertx.up.uca.options.Transformer;
import io.zerows.core.vertx.RpcOptions;

public class RpcServerSetUp implements Transformer<RpcOptions> {

    private static final Annal LOGGER = Annal.get(RpcServerSetUp.class);

    @Override
    public RpcOptions transform(final JsonObject input) {
        return Fn.runOr(null == input, LOGGER,
            RpcOptions::new,
            () -> new RpcOptions(input));
    }
}
