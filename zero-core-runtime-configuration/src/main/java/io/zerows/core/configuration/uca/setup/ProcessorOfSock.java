package io.zerows.core.configuration.uca.setup;

import io.horizon.eon.em.web.ServerType;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.vertx.up.util.Ut;
import io.zerows.core.configuration.atom.NodeVertx;
import io.zerows.core.configuration.atom.option.SockOptions;
import io.zerows.core.configuration.uca.transformer.SockTransformer;
import io.zerows.core.configuration.zdk.Processor;
import io.zerows.core.configuration.zdk.Transformer;
import io.zerows.core.metadata.uca.environment.MatureOn;

/**
 * @author lang : 2024-04-20
 */
class ProcessorOfSock implements Processor<NodeVertx, JsonArray> {

    private transient final Transformer<SockOptions> sockTransformer;

    ProcessorOfSock() {
        this.sockTransformer = new SockTransformer();
    }


    @Override
    public void makeup(final NodeVertx target, final JsonArray setting) {
        this.tracker().debug(INFO.V_BEFORE, KName.SERVER, ServerType.SOCK, setting);


        Ut.itJArray(setting, (item, index) -> {


            JsonObject configureJ = Ut.valueJObject(item, KName.CONFIG);
            configureJ = MatureOn.envSock(configureJ, index);


            final SockOptions options = this.sockTransformer.transform(configureJ);
            final String serverName = Ut.valueString(item, KName.NAME);
            target.optionServer(serverName, options);
        });
        if (Ut.isNotNil(setting)) {
            this.tracker().info(INFO.V_AFTER, KName.SERVER, ServerType.SOCK, setting);
        }
    }
}
