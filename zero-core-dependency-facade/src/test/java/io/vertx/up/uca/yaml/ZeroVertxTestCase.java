package io.vertx.up.uca.yaml;

import io.vertx.core.json.JsonObject;
import io.zerows.core.feature.unit.testing.ZeroBase;
import io.zerows.core.metadata.store.config.OZeroStore;
import org.junit.Test;

public class ZeroVertxTestCase extends ZeroBase {

    @Test
    public void testVertxRead() {
        final JsonObject data = OZeroStore.containerJ();
        System.out.println(data.encodePrettily());
    }
}
