package io.zerows.core.metadata.store.classes;

import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.up.util.Ut;
import io.zerows.core.metadata.uca.scanner.ClassScanner;
import io.zerows.core.metadata.zdk.AbstractAmbiguity;
import io.zerows.core.metadata.zdk.running.OCache;

import java.util.Set;

/**
 * @author lang : 2024-04-17
 */
class OCachedWeb extends AbstractAmbiguity implements OCache<Set<Class<?>>> {

    private static final Set<Class<?>> CLASSES = new ConcurrentHashSet<>();

    OCachedWeb() {
        super();
        {
            final ClassScanner scanner = ClassScanner.of();
            final Set<Class<?>> scanned = scanner.scan(Thread.currentThread().getContextClassLoader());
            Ut.Log.metadata(this.getClass()).info(OCachedClass.INFO_SCANNED, String.valueOf(scanned.size()));
            CLASSES.addAll(scanned);
        }
        // bundle = null
    }

    @Override
    public Set<Class<?>> get() {
        return CLASSES;
    }

    @Override
    public OCache<Set<Class<?>>> add(final Set<Class<?>> classes) {
        CLASSES.addAll(classes);
        return this;
    }

    @Override
    public OCache<Set<Class<?>>> remove(final Set<Class<?>> classes) {
        CLASSES.removeAll(classes);
        return this;
    }
}
