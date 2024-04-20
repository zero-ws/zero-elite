package io.zerows.core.configuration.uca.visitor;

import io.horizon.eon.VMessage;
import io.horizon.eon.em.web.ServerType;
import io.horizon.exception.ProgramException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.vertx.up.util.Ut;
import io.zerows.core.configuration.atom.option.SockOptions;
import io.zerows.core.configuration.uca.setup.SockTransformer;
import io.zerows.core.configuration.zdk.ServerVisitor;
import io.zerows.core.configuration.zdk.Transformer;
import io.zerows.core.metadata.uca.environment.MatureOn;
import io.zerows.core.metadata.uca.stable.Ruler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public class SockVisitor extends AbstractVisitor implements ServerVisitor<SockOptions> {
    protected transient final Transformer<SockOptions>
        transformer = Ut.singleton(SockTransformer.class);

    @Override
    public ConcurrentMap<Integer, SockOptions> visit(final String... key)
        throws ProgramException {
        final JsonArray serverData = this.serverPre(0, key);
        this.logger().debug(VMessage.Visitor.V_BEFORE, KName.SERVER, this.serverType(), serverData.encode());
        Ruler.verify(KName.SERVER, serverData);
        final ConcurrentMap<Integer, SockOptions> map =
            new ConcurrentHashMap<>();
        this.extract(serverData, map);
        if (!map.isEmpty()) {
            this.logger().info(VMessage.Visitor.V_AFTER, KName.SERVER, this.serverType(), map.keySet());
        }
        return map;
    }

    protected void extract(final JsonArray serverData, final ConcurrentMap<Integer, SockOptions> map) {
        /*
         * 多服务器模式：Server可使用环境变量，环境变量后缀为索引值（0抹去）
         */
        Ut.itJArray(serverData, (item, index) -> {
            // 合法 Server
            JsonObject configureJ = Ut.valueJObject(item, KName.CONFIG);
            configureJ = MatureOn.envSock(configureJ, index);
            // 1. Extract port
            final int port = this.serverPort(configureJ);
            // 2. Convert JsonObject to SockOptions
            final SockOptions options = this.transformer.transform(item);
            // 3. Add to map
            map.put(port, options);
        });
    }

    @Override
    public ServerType serverType() {
        return ServerType.SOCK;
    }
}
