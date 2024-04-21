package io.zerows.core.metadata.store;

import io.horizon.eon.VName;
import io.horizon.eon.VString;
import io.horizon.spi.boot.HEquip;
import io.horizon.uca.cache.Cc;
import io.macrocosm.specification.config.HConfig;
import io.macrocosm.specification.config.HSetting;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.vertx.up.eon.configure.YmlCore;
import io.vertx.up.util.Ut;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lang : 2024-04-17
 */
public class OCacheFailure implements OCache<JsonObject> {
    private static final ConcurrentMap<String, JsonObject> STORE_DATA = new ConcurrentHashMap<>();
    private static final Cc<String, OCache<JsonObject>> CC_FAILURE = Cc.open();
    private static OCache<JsonObject> INSTANCE;
    private static HSetting SETTING;
    private final Bundle bundle;

    private OCacheFailure(final Bundle bundle) {
        this.bundle = bundle;
    }

    @SuppressWarnings("unchecked")
    public static <T extends OCache<JsonObject>> T of(final Bundle bundle) {
        if (Objects.isNull(bundle)) {
            if (INSTANCE == null) {
                INSTANCE = new OCacheFailure(null);
            }
            return (T) INSTANCE;
        } else {
            final String key = bundle.getSymbolicName() + VString.SLASH + bundle.getVersion().getQualifier();
            return (T) CC_FAILURE.pick(() -> new OCacheFailure(bundle), key);
        }
    }

    public void initialize() {
        final HEquip equip = OZeroEquip.of(this.bundle);
        SETTING = equip.initialize();
        {
            final HConfig error = SETTING.infix(YmlCore.error.__KEY);
            final JsonObject errorData = Ut.valueJObject(error.options(), KName.ERROR);
            STORE_DATA.put(KName.ERROR, errorData);

            final JsonObject infoData = Ut.valueJObject(error.options(), KName.INFO);
            STORE_DATA.put(KName.INFO, infoData);
        }
    }

    public HSetting setting() {
        return SETTING;
    }

    @Override
    public JsonObject get(final String key) {
        return STORE_DATA.getOrDefault(key, new JsonObject());
    }

    @Override
    public OCache<JsonObject> add(final JsonObject errorYml) {
        // error
        final JsonObject error = Ut.valueJObject(errorYml, VName.ERROR);
        Ut.Log.exception(this.getClass())
            .info("\"{}\" errors has been added to the Store.", error.size());
        {
            final JsonObject errorData = STORE_DATA.computeIfAbsent(VName.ERROR, k -> new JsonObject());
            errorData.mergeIn(error, true);
            STORE_DATA.put(VName.ERROR, errorData);
        }

        // info
        final JsonObject info = Ut.valueJObject(errorYml, VName.INFO);
        {
            final JsonObject infoData = STORE_DATA.computeIfAbsent(VName.INFO, k -> new JsonObject());
            infoData.mergeIn(info, true);
            STORE_DATA.put(VName.INFO, infoData);
        }
        return this;
    }

    @Override
    public OCache<JsonObject> remove(final JsonObject errorYml) {
        // error
        final JsonObject error = Ut.valueJObject(errorYml, VName.ERROR);

        Ut.Log.exception(this.getClass())
            .info("\"{}\" errors has been removed from the Store.", error.fieldNames().size());
        {
            final JsonObject errorData = STORE_DATA.computeIfAbsent(VName.ERROR, k -> new JsonObject());
            error.fieldNames().forEach(errorData::remove);
            STORE_DATA.put(VName.ERROR, errorData);
        }


        // info
        final JsonObject info = Ut.valueJObject(errorYml, VName.INFO);
        {
            final JsonObject infoData = STORE_DATA.computeIfAbsent(VName.INFO, k -> new JsonObject());
            info.fieldNames().forEach(infoData::remove);
            STORE_DATA.put(VName.INFO, infoData);
        }
        return this;
    }


}
