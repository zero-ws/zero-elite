package io.zerows.core.metadata.zdk.service;

import io.horizon.uca.cache.Cc;
import io.vertx.up.eon.KName;
import io.zerows.core.metadata.osgi.service.ExceptionDesk;
import io.zerows.core.metadata.uca.callback.SettingCallback;
import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;
import org.osgi.framework.Bundle;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author lang : 2024-03-28
 */
public abstract class AbstractServiceConnector implements ServiceConnector {
    protected static Cc<String, ServiceConnector> CCT_REFS = Cc.open();
    protected Bundle bundle;

    protected AbstractServiceConnector(final Bundle bundle) {
        this.bundle = bundle;
    }

    protected static ServiceConnector of(final Bundle bundle, final Function<Bundle, ServiceConnector> constructor) {
        final String key = constructor.apply(bundle).getClass().getName() + bundle.hashCode();
        return CCT_REFS.pick(() -> constructor.apply(bundle), key);
    }

    @Override
    public void serviceDependency(final DependencyManager dm,
                                  final Supplier<Component> supplier,
                                  final Supplier<ServiceDependency> serviceSupplier) {
        dm.add(supplier.get().setImplementation(new SettingCallback(this.bundle))
            // ExceptionDesk
            .add(serviceSupplier.get().setService(ExceptionDesk.class)
                .setRequired(Boolean.TRUE).setCallbacks(KName.START, KName.STOP))
        );
    }

    @Override
    public void serviceRegister(final DependencyManager dm, final Supplier<Component> supplier) {

    }
}
