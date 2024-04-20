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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

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
    private static final ConcurrentMap<String, AtomicInteger> SERVER_LOG = new ConcurrentHashMap<>();
    @SuppressWarnings("all")
    private final ConcurrentMap<String, OptionOfServer> serverOptions = new ConcurrentHashMap<>();
    private final ConcurrentMap<Class<?>, DeploymentOptions> deploymentOptions =
        new ConcurrentHashMap<>();
    private final String vertxName;
    private VertxOptions vertxOptions;
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

    public void optionVertx(final VertxOptions vertxOptions) {
        this.vertxOptions = vertxOptions;
    }

    public VertxOptions optionVertx() {
        return this.vertxOptions;
    }

    public void optionDeployment(final Class<?> component,
                                 final DeploymentOptions deploymentOptions) {
        this.deploymentOptions.put(component, deploymentOptions);
    }

    public DeploymentOptions optionDeployment(final Class<?> component) {
        return this.deploymentOptions.get(component);
    }

    public void optionDelivery(final DeliveryOptions deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public DeliveryOptions optionDelivery() {
        return this.deliveryOptions;
    }

    // 当前节点所属 Server 配置
    public void optionServer(final String name,
                             final ServerType type,
                             final HttpServerOptions serverOptions) {
        this.serverOptions.put(name, OptionBuilder.ofHttp(name, type, serverOptions));
        SERVER_LOG.put(name, new AtomicInteger(0));
    }

    public void optionServer(final String name, final RpcOptions serverOptions) {
        this.serverOptions.put(name, OptionBuilder.ofRpc(name, serverOptions));
        SERVER_LOG.put(name, new AtomicInteger(0));
    }

    public void optionServer(final String name, final SockOptions serverOptions) {
        this.serverOptions.put(name, OptionBuilder.ofSock(name, serverOptions));
        SERVER_LOG.put(name, new AtomicInteger(0));
    }

    @SuppressWarnings("all")
    public <T> OptionOfServer<T> optionServer(final String name) {
        return (OptionOfServer<T>) this.serverOptions.get(name);
    }

    public Set<String> optionServers(final ServerType type) {
        if (Objects.isNull(type)) {
            return this.serverOptions.keySet();
        } else {
            final Set<String> servers = new HashSet<>();
            this.serverOptions.forEach((serverName, option) -> {
                if (type == option.type()) {
                    servers.add(serverName);
                }
            });
            return servers;
        }
    }

    // 日志处理（必须）
    public AtomicInteger loggerOfServer(final String serverName) {
        return SERVER_LOG.get(serverName);
    }
}
