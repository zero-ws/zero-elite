package io.zerows.core.metadata.store.classes;

import io.vertx.up.eon.KMeta;
import io.zerows.core.metadata.zdk.running.OCache;

import java.util.Set;
import java.util.function.Function;

/**
 * @author lang : 2024-04-19
 */
public interface OClassProcessor extends OCache<Set<Class<?>>> {

    OClassProcessor compile(KMeta.Typed type, Function<Set<Class<?>>, Set<Class<?>>> compiler);
}
