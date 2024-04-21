package io.zerows.core.metadata.store;

import io.vertx.up.eon.KMeta;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

import java.util.Set;
import java.util.function.Function;

/**
 * @author lang : 2024-04-19
 */
public interface OCacheClass extends OCache<Set<Class<?>>> {

    static OCacheClass of() {
        return OCacheClassAmbiguity.of();
    }

    static OCacheClass of(final Bundle bundle) {
        return OCacheClassAmbiguity.of(bundle);
    }

    OCacheClass compile(KMeta.Typed type, Function<Set<Class<?>>, Set<Class<?>>> compiler);

    Set<Class<?>> get(KMeta.Typed type);

    OCacheClass remove(KMeta.Typed type);

    OCacheClass remove(Class<?> clazz);
}
