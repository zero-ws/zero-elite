package io.vertx.up.uca.yaml;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.up.eon.configure.YmlCore;
import io.zerows.core.facade.junit.ZeroBase;
import io.zerows.core.metadata.store.config.OZeroStore;
import org.junit.Test;

public class ZeroErrorTestCase extends ZeroBase {

    @Test
    public void testError(final TestContext context) {
        final JsonObject errorJ = OZeroStore.option(YmlCore.error.__KEY);
        System.out.println(errorJ);
    }
}
