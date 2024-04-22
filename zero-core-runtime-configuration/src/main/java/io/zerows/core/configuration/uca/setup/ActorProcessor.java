package io.zerows.core.configuration.uca.setup;

import io.macrocosm.specification.config.HConfig;
import io.macrocosm.specification.config.HSetting;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.configure.YmlCore;
import io.vertx.up.util.Ut;
import io.zerows.core.configuration.atom.NodeVertx;
import io.zerows.core.configuration.atom.option.ActorOptions;
import io.zerows.core.configuration.uca.transformer.ActorTransformer;
import io.zerows.core.configuration.zdk.Processor;
import io.zerows.core.configuration.zdk.Transformer;

import java.util.Objects;

/**
 * @author lang : 2024-04-20
 */
class ActorProcessor implements Processor<NodeVertx, HSetting> {
    private final transient Transformer<ActorOptions> transformerActor;

    ActorProcessor() {
        this.transformerActor = Ut.singleton(ActorTransformer.class);
    }

    @Override
    public void makeup(final NodeVertx target, final HSetting setting) {
        if (!setting.hasInfix(YmlCore.deployment.__KEY)) {
            this.logger().info(INFO.V_DEPLOYMENT);
            return;
        }
        final HConfig config = setting.infix(YmlCore.deployment.__KEY);
        if (Objects.isNull(config)) {
            return;
        }
        final JsonObject options = config.options();
        if (Ut.isNil(options)) {
            return;
        }

        // DeliveryOptions and DeploymentOptions
        final ActorOptions actorOptions = this.transformerActor.transform(options);
        target.optionDelivery(actorOptions.optionDelivery());
        actorOptions.optionDeploy().forEach(target::optionDeployment);
    }
}
