package io.zerows.core.configuration.uca.setup;

import io.horizon.uca.log.Annal;
import io.vertx.core.json.JsonObject;
import io.vertx.up.fn.Fn;
import io.zerows.core.configuration.atom.option.ClusterOptions;
import io.zerows.core.configuration.zdk.Transformer;

public class ClusterSetUp implements Transformer<ClusterOptions> {

    private static final Annal LOGGER = Annal.get(ClusterSetUp.class);

    @Override
    public ClusterOptions transform(final JsonObject config) {
        return Fn.runOr(null == config, LOGGER,
            ClusterOptions::new,
            () -> new ClusterOptions(config));
    }
}
