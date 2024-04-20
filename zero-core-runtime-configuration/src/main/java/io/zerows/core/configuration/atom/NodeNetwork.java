package io.zerows.core.configuration.atom;

import io.zerows.core.configuration.atom.option.ClusterOptions;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 配置管理专用结构，用于管理 Options，最终会以服务的方式转出去形成统一配置，一个节点
 * <pre><code>
 *     1. ClusterOptions 只有一个，交给 AdminCluster 管理
 *        所有的 NodeNetwork 有一个指向 ClusterOptions 的引用
 *     2. VertxOptions 可能存在多个
 *        name = VertxOptions 的结构，此处的 NodeNetwork 中就会包含多个 Vertx 配置
 *     3. DeploymentOptions 的数量在 VertxOptions 之下，根据配置的 Verticle 来定义
 *        DeliveryOptions 的数量和 VertxOptions 的数量和 EventBus 数量一致
 *     4. ServerOptions 的数量同样在 VertxOptions 之下，根据配置的 Server 来定义
 * </code></pre>
 * 所以此处会出现一种正交状态，而 NodeNetwork 做线性结构，最终可根据 key 直接计算
 *
 * @author lang : 2024-04-20
 */
public class NodeNetwork implements Serializable {

    private final ConcurrentMap<String, NodeVertx> vertxOptions = new ConcurrentHashMap<>();
    private ClusterOptions clusterOptions;

    public NodeNetwork() {
    }

    // 当前节点所属集群配置
    public NodeNetwork cluster(final ClusterOptions clusterOptions) {
        this.clusterOptions = clusterOptions;
        return this;
    }

    public ClusterOptions cluster() {
        return this.clusterOptions;
    }

    // 当前节点所属 Vertx 配置
    public NodeNetwork add(final String name, final NodeVertx vertxOptions) {
        this.vertxOptions.put(name, vertxOptions);
        return this;
    }

    public void remove(final String name) {
        this.vertxOptions.remove(name);
    }

    public NodeVertx get(final String name) {
        return this.vertxOptions.get(name);
    }
}
