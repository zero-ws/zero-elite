package io.zerows.core.configuration.uca.visitor;

import io.horizon.eon.VMessage;
import io.horizon.eon.em.web.ServerType;
import io.horizon.exception.ProgramException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.vertx.up.eon.configure.YmlCore;
import io.vertx.up.fn.Fn;
import io.vertx.up.util.Ut;
import io.zerows.core.configuration.atom.option.RpcOptions;
import io.zerows.core.configuration.exception.ServerConfigException;
import io.zerows.core.configuration.uca.setup.RpcTransformer;
import io.zerows.core.configuration.zdk.ServerVisitor;
import io.zerows.core.configuration.zdk.Transformer;
import io.zerows.core.metadata.store.config.OZeroStore;
import io.zerows.core.metadata.uca.stable.Ruler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Rpc options only, it's different from Http
 */
public class RpcServerVisitor implements ServerVisitor<RpcOptions> {

    //    private transient final Node<JsonObject> node = Node.infix(YmlCore.server.__KEY);
    private transient final Transformer<RpcOptions>
        transformer = Ut.singleton(RpcTransformer.class);

    @Override
    @SuppressWarnings("all")
    public ConcurrentMap<Integer, RpcOptions> visit(final String... key)
        throws ProgramException {
        // 1. Must be the first line, fixed position.
        //        Fn.verifyLenEq(this.getClass(), 0, key);
        // 2. Visit the node for server, http
        final JsonObject data = OZeroStore.option(YmlCore.server.__KEY); // this.node.read();

        Fn.outBug(null == data || !data.containsKey(KName.SERVER), logger(),
            ServerConfigException.class,
            this.getClass(), null == data ? null : data.encode());

        return this.visit(data.getJsonArray(KName.SERVER));
    }

    private ConcurrentMap<Integer, RpcOptions> visit(final JsonArray serverData)
        throws ProgramException {
        this.logger().debug(VMessage.Visitor.V_BEFORE, KName.SERVER, ServerType.IPC, serverData.encode());
        Ruler.verify(KName.SERVER, serverData);
        final ConcurrentMap<Integer, RpcOptions> map =
            new ConcurrentHashMap<>();
        Ut.itJArray(serverData, JsonObject.class, (item, index) -> {
            if (this.isServer(item)) {
                // 1. Extract port
                final int port = this.serverPort(item.getJsonObject(KName.CONFIG));
                // 2. Convert JsonObject to HttpServerOptions
                final RpcOptions options = this.transformer.transform(item);
                Fn.runAt(() -> {
                    // 3. Add to map;
                    map.put(port, options);
                }, port, options);
            }
        });
        if (!map.isEmpty()) {
            this.logger().info(VMessage.Visitor.V_AFTER, KName.SERVER, ServerType.IPC, map.keySet());
        }
        return map;
    }

    @Override
    public int serverPort(final JsonObject config) {
        if (null != config) {
            return config.getInteger(KName.PORT, RpcOptions.DEFAULT_PORT);
        }
        return RpcOptions.DEFAULT_PORT;
    }

    @Override
    public boolean isServer(final JsonObject item) {
        return ServerType.IPC.match(item.getString(KName.TYPE));
    }
}
