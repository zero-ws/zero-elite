package io.zerows.core.configuration.uca.setup;

import io.horizon.uca.log.Annal;
import io.vertx.core.json.JsonObject;
import io.vertx.up.fn.Fn;
import io.zerows.core.configuration.atom.option.RpcOptions;
import io.zerows.core.configuration.zdk.Transformer;

public class RpcServerSetUp implements Transformer<RpcOptions> {

    private static final Annal LOGGER = Annal.get(RpcServerSetUp.class);

    @Override
    public RpcOptions transform(final JsonObject input) {
        return Fn.runOr(null == input, LOGGER,
            RpcOptions::new,
            () -> new RpcOptions(input));
    }
}
