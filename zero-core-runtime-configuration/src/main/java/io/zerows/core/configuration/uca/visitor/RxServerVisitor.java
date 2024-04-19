package io.zerows.core.configuration.uca.visitor;

import io.horizon.eon.em.web.ServerType;

public class RxServerVisitor extends HttpServerVisitor {

    @Override
    public ServerType serverType() {
        return ServerType.RX;
    }
}
