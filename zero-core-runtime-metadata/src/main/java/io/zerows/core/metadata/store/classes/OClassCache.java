package io.zerows.core.metadata.store.classes;

import io.vertx.up.eon.KMeta;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

import java.util.Set;
import java.util.function.Function;

/**
 * @author lang : 2024-04-19
 */
public interface OClassCache extends OCache<Set<Class<?>>> {

    static OClassCache of() {
        return OClassCacheAmbiguity.of();
    }

    static OClassCache of(final Bundle bundle) {
        return OClassCacheAmbiguity.of(bundle);
    }

    OClassCache compile(KMeta.Typed type, Function<Set<Class<?>>, Set<Class<?>>> compiler);

    Set<Class<?>> get(KMeta.Typed type);

    OClassCache remove(KMeta.Typed type);

    OClassCache remove(Class<?> clazz);
}
