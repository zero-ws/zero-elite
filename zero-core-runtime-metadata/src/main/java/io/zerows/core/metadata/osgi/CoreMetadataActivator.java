package io.zerows.core.metadata.osgi;

import io.vertx.up.util.Ut;
import io.zerows.core.metadata.eon.MessageOfMeta;
import io.zerows.core.metadata.osgi.command.CommandFailure;
import io.zerows.core.metadata.osgi.command.OCommand;
import io.zerows.core.metadata.zdk.service.ServiceConnector;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.annotation.bundle.Header;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * @author lang : 2024-04-17
 */
@Header(name = Constants.BUNDLE_ACTIVATOR, value = "${@class}")
public class CoreMetadataActivator extends DependencyActivatorBase {
    @Override
    public void init(final BundleContext context, final DependencyManager dm) throws Exception {

        final Bundle bundle = context.getBundle();

        final ServiceConnector connector = CoreMetadataDependency.of(bundle);

        connector.serviceDependency(dm, this::createComponent, this::createServiceDependency);
        connector.serviceRegister(dm, this::createComponent);

        // Command
        {
            context.registerService(CommandFailure.class.getName(),
                new CommandFailure(context), Ut.Bnd.command(OCommand.STORE));
        }
        Ut.Log.bundle(this.getClass())
            .info(MessageOfMeta.Osgi.BUNDLE.START, bundle.getSymbolicName());
    }
}
