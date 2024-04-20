package io.zerows.core.configuration.uca.transformer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.ThreadingModel;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Agent;
import io.vertx.up.annotations.Worker;
import io.vertx.up.util.Ut;
import io.zerows.core.configuration.atom.option.ActorOptions;
import io.zerows.core.configuration.zdk.Transformer;
import io.zerows.core.metadata.eon.em.EmDeploy;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * 原始数据转换器，此数据转换器会带上对应的默认值执行智能化判断和相关操作
 * <pre><code>
 *     1. {@link io.vertx.core.DeploymentOptions} 一个类拥有一个配置
 *     2. {@link io.vertx.core.eventbus.DeliveryOptions} 全局一个配置
 * </code></pre>
 *
 * @author lang : 2024-04-20
 */
public class ActorTransformer implements Transformer<ActorOptions> {
    /**
     * 如果编程和配置冲突，则计算 EmDeploy.Mode 来判断
     *
     * @param input 输入的 JsonObject 数据
     *
     * @return {@link ActorOptions} 返回转换后的配置数据
     */
    @Override
    public ActorOptions transform(final JsonObject input) {
        // 初始序列化
        final ActorOptions actorOptions = Ut.deserialize(input, ActorOptions.class);
        /*
         * Deployment 的默认判断条件
         * - @Worker
         * - @Job
         * - @Agent
         * 三种模式判断，对应到 Vert.x 的三种模式
         */
        final EmDeploy.Mode mode = actorOptions.getMode();
        this.tracker().info(INFO.INFO_ROTATE, mode);


        /* DeploymentOptions 初始化 */
        {
            // class = DeploymentOptions 计算
            final JsonObject options = actorOptions.getOptions();
            options.fieldNames().forEach((className) -> {
                /* clazz = options ( JsonObject ) */
                final Class<?> clazz = Ut.clazz(className);
                final JsonObject option = Ut.valueJObject(options, className);


                /*
                 * DeploymentOptions 初始化
                 * 1. 从配置中构造
                 * 2. 从类中提取 ha 和 instances 配置
                 */
                final Annotation annotation = this.getAnnotation(clazz);
                final DeploymentOptions deploymentOptions = this.initOfOption(option, annotation);


                /*
                 * 在 ActorOptions 中追加相关信息
                 */
                actorOptions.optionDeploy(clazz, deploymentOptions);
            });
        }


        /* DeliveryOptions 初始化 */
        {
            final JsonObject delivery = actorOptions.getDelivery();
            final DeliveryOptions deliveryOptions = new DeliveryOptions();
            deliveryOptions.setSendTimeout(delivery.getLong("timeout", deliveryOptions.getSendTimeout()));


            this.tracker().info(INFO.INFO_DELIVERY, deliveryOptions.toJson());
            actorOptions.optionDelivery(deliveryOptions);
        }
        return actorOptions;
    }

    private Annotation getAnnotation(final Class<?> clazz) {
        Annotation annotation = clazz.getDeclaredAnnotation(Worker.class);
        if (Objects.isNull(annotation)) {
            annotation = clazz.getDeclaredAnnotation(Agent.class);
        }
        return annotation;
    }

    private DeploymentOptions initOfOption(final JsonObject options, final Annotation annotation) {
        final JsonObject optionData = Ut.valueJObject(options);
        final DeploymentOptions deploymentOptions = new DeploymentOptions(optionData);
        if (Worker.class == annotation.annotationType()) {
            // Worker
            deploymentOptions.setThreadingModel(ThreadingModel.WORKER);
            /* BUG: workerPoolSize */
            final Integer workerPoolSize = optionData
                .getInteger("workerPoolSize", deploymentOptions.getWorkerPoolSize());
            deploymentOptions.setWorkerPoolSize(workerPoolSize);


            /* virtual */
            final Boolean multi = options.getBoolean("virtual", Boolean.FALSE);
            if (multi) {
                deploymentOptions.setThreadingModel(ThreadingModel.VIRTUAL_THREAD);
            }
        } else {
            // Agent
            deploymentOptions.setThreadingModel(ThreadingModel.EVENT_LOOP);
        }
        {
            // ha processing
            final boolean ha = Ut.invoke(annotation, "ha");
            deploymentOptions.setHa(ha);
            // instances
            final int instances = Ut.invoke(annotation, "instances");
            deploymentOptions.setInstances(instances);
        }
        this.tracker().info(INFO.INFO_VTC, deploymentOptions.getInstances(), deploymentOptions.isHa(), deploymentOptions.toJson());
        return deploymentOptions;
    }
}
