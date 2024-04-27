package io.zerows.core.metadata.osgi.command.failure;

import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.zerows.core.metadata.store.OCacheFailure;
import io.zerows.core.metadata.zdk.running.OCommander;
import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-22
 */
class FailureAll implements OCommander {

    @Override
    public void execute(final Bundle caller) {
        // 提取异常表
        final OCacheFailure cache = OCacheFailure.of(caller);
        final JsonObject stored = cache.get(KName.ERROR);
        // 拉取异常报表
        System.out.println("Stored error data: " + stored.encodePrettily());
    }
}
