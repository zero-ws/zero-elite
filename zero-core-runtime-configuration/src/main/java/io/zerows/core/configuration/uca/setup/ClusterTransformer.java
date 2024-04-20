package io.zerows.core.configuration.uca.setup;

import io.vertx.core.json.JsonObject;
import io.vertx.up.fn.Fn;
import io.zerows.core.configuration.atom.option.ClusterOptions;
import io.zerows.core.configuration.zdk.Transformer;

public class ClusterTransformer implements Transformer<ClusterOptions> {

    @Override
    public ClusterOptions transform(final JsonObject config) {
        return Fn.runOr(null == config, this.tracker(),
            ClusterOptions::new,
            () -> new ClusterOptions(config));
    }
}
