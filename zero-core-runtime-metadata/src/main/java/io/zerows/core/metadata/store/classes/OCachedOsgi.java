package io.zerows.core.metadata.store.classes;

import io.vertx.core.impl.ConcurrentHashSet;
import io.zerows.core.metadata.uca.scanner.ClassScanner;
import io.zerows.core.metadata.zdk.AbstractAmbiguity;
import io.zerows.core.metadata.zdk.running.OCache;
import org.osgi.framework.Bundle;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lang : 2024-04-17
 */
class OCachedOsgi extends AbstractAmbiguity implements OCache<Set<Class<?>>> {

    private static final ConcurrentMap<String, Set<Class<?>>> CLASSES_MAP = new ConcurrentHashMap<>();

    OCachedOsgi(final Bundle bundle) {
        super(bundle);
        {
            final ClassScanner scanner = ClassScanner.of();
            final Set<Class<?>> scanned = scanner.scan(bundle.getClass().getClassLoader());
            CLASSES_MAP.put(this.key(false), scanned);
        }
    }

    @Override
    public Set<Class<?>> get() {
        return CLASSES_MAP.values().stream().reduce((left, right) -> {
            left.addAll(right);
            return left;
        }).orElseGet(ConcurrentHashSet::new);
    }

    @Override
    public OCache<Set<Class<?>>> add(final Set<Class<?>> classes) {
        final String key = this.key(false);
        CLASSES_MAP.put(key, classes);
        return this;
    }

    @Override
    public OCache<Set<Class<?>>> remove(final Set<Class<?>> classes) {
        final String key = this.key(false);
        CLASSES_MAP.remove(key);
        return this;
    }
}
