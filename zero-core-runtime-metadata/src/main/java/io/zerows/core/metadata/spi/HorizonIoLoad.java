package io.zerows.core.metadata.spi;

import io.horizon.spi.HorizonIo;
import io.horizon.spi.boot.HEquip;
import io.horizon.util.HUt;
import io.macrocosm.specification.config.HConfig;
import io.macrocosm.specification.config.HSetting;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.vertx.up.eon.configure.YmlCore;
import io.zerows.core.metadata.store.config.OZeroEquip;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author lang : 2023/4/28
 */
public class HorizonIoLoad implements HorizonIo {
    private final HSetting setting;

    public HorizonIoLoad() {
        final Bundle bundle = FrameworkUtil.getBundle(this.getClass());
        final HEquip equip = OZeroEquip.of(bundle);
        this.setting = equip.initialize();
    }

    @Override
    public JsonObject ofError() {
        final HConfig error = this.setting.infix(YmlCore.error.__KEY);
        return HUt.valueJObject(error.options(), KName.ERROR);
    }

    @Override
    public JsonObject ofFailure() {
        final HConfig error = this.setting.infix(YmlCore.error.__KEY);
        return HUt.valueJObject(error.options(), KName.INFO);
    }
}
