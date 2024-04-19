package io.vertx.up.runtime;

import io.vertx.ext.unit.TestContext;
import io.zerows.core.feature.unit.testing.ZeroBase;
import io.zerows.core.metadata.store.classes.OClassCache;
import io.zerows.core.metadata.zdk.running.OCache;
import org.junit.Test;

import java.util.Set;

public class ZeroPackTc extends ZeroBase {

    @Test
    public void testScan(final TestContext context) {
        final OCache<Set<Class<?>>> cached = OClassCache.of();
        final Set<Class<?>> clazzes = cached.get();
        for (final Class<?> clazz : clazzes) {
            // System.out.println(clazz);
        }
        context.assertNotNull(clazzes);
    }
}
