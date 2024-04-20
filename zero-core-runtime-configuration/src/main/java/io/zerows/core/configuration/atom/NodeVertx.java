package io.zerows.core.configuration.atom;

import io.horizon.eon.em.web.ServerType;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpServerOptions;
import io.zerows.core.configuration.atom.option.RpcOptions;
import io.zerows.core.configuration.atom.option.SockOptions;
import io.zerows.core.configuration.atom.server.OptionBuilder;
import io.zerows.core.configuration.zdk.OptionOfServer;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Vertx 专用节点，清单
 * <pre><code>
 *     1. VertxOptions 一个
 *     2. DeploymentOptions 三个
 *        - 默认的 Agent
 *        - 默认的 Worker
 *        - 默认的 Scheduler
 *     3. DeliveryOptions 一个
 *     4. ServerOptions N 个
 * </code></pre>
 *
 * @author lang : 2024-04-20
 */
public class NodeVertx implements Serializable {
    @SuppressWarnings("all")
    private final ConcurrentMap<String, OptionOfServer> serverOptions = new ConcurrentHashMap<>();
    private final String vertxName;
    private VertxOptions vertxOptions;
    private DeploymentOptions agentOptions;
    private DeploymentOptions workerOptions;
    private DeploymentOptions schedulerOptions;
    private DeliveryOptions deliveryOptions;

    private NodeVertx(final String vertxName) {
        this.vertxName = vertxName;
    }

    public static NodeVertx of(final String vertxName) {
        return new NodeVertx(vertxName);
    }

    public String name() {
        return this.vertxName;
    }

    public NodeVertx optionVertx(final VertxOptions vertxOptions) {
        this.vertxOptions = vertxOptions;
        return this;
    }

    public VertxOptions optionVertx() {
        return this.vertxOptions;
    }

    public NodeVertx optionAgent(final DeploymentOptions agentOptions) {
        this.agentOptions = agentOptions;
        return this;
    }

    public DeploymentOptions optionAgent() {
        return this.agentOptions;
    }

    public NodeVertx optionWorker(final DeploymentOptions workerOptions) {
        this.workerOptions = workerOptions;
        return this;
    }

    public DeploymentOptions optionWorker() {
        return this.workerOptions;
    }

    public NodeVertx optionScheduler(final DeploymentOptions schedulerOptions) {
        this.schedulerOptions = schedulerOptions;
        return this;
    }

    public DeploymentOptions optionScheduler() {
        return this.schedulerOptions;
    }

    public NodeVertx optionDelivery(final DeliveryOptions deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
        return this;
    }

    public DeliveryOptions optionDelivery() {
        return this.deliveryOptions;
    }

    // 当前节点所属 Server 配置
    public NodeVertx optionServer(final String name,
                                  final ServerType type,
                                  final HttpServerOptions serverOptions) {
        this.serverOptions.put(name, OptionBuilder.ofHttp(name, type, serverOptions));
        return this;
    }

    public NodeVertx optionServer(final String name, final RpcOptions serverOptions) {
        this.serverOptions.put(name, OptionBuilder.ofRpc(name, serverOptions));
        return this;
    }

    public NodeVertx optionServer(final String name, final SockOptions serverOptions) {
        this.serverOptions.put(name, OptionBuilder.ofSock(name, serverOptions));
        return this;
    }

    @SuppressWarnings("all")
    public <T> OptionOfServer<T> optionServer(final String name) {
        return (OptionOfServer<T>) this.serverOptions.get(name);
    }
}
