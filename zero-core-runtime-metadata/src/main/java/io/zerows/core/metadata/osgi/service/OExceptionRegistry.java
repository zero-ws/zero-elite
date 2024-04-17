package io.zerows.core.metadata.osgi.service;

import io.horizon.spi.boot.HEquip;
import io.macrocosm.specification.config.HConfig;
import io.macrocosm.specification.config.HSetting;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.configure.YmlCore;
import io.zerows.core.metadata.store.config.OZeroEquip;
import io.zerows.core.metadata.store.config.OZeroFailure;
import io.zerows.core.metadata.zdk.running.ORegistry;
import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-17
 */
public class OExceptionRegistry implements ORegistry {

    @Override
    public void install(final Bundle bundle) {
        final OZeroFailure cache = OZeroFailure.of(bundle);
        cache.add(this.ofError(bundle));
    }

    @Override
    public void uninstall(final Bundle bundle) {
        final OZeroFailure cache = OZeroFailure.of(bundle);
        cache.remove(this.ofError(bundle));
    }

    private JsonObject ofError(final Bundle bundle) {
        final HEquip equip = OZeroEquip.of(bundle);
        final HSetting setting = equip.initialize();
        final HConfig error = setting.infix(YmlCore.error.__KEY);
        return error.options();
    }
}
