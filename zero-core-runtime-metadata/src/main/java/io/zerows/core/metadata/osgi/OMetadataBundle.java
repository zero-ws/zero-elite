package io.zerows.core.metadata.osgi;

import io.vertx.up.eon.MessageOfLog;
import io.vertx.up.util.Ut;
import io.zerows.core.metadata.osgi.service.OExceptionRegistry;
import io.zerows.core.metadata.uca.command.CommandError;
import io.zerows.core.metadata.zdk.running.ORegistry;
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
public class OMetadataBundle extends DependencyActivatorBase {
    @Override
    public void init(final BundleContext context, final DependencyManager dm) throws Exception {

        final Bundle bundle = context.getBundle();

        // ORegistry
        {
            dm.add(this.createComponent()
                .setInterface(ORegistry.class, null)
                .setImplementation(OExceptionRegistry.class)
            );
            Ut.Log.service(this.getClass())
                .info(MessageOfLog.SERVICE.REGISTER, OExceptionRegistry.class, ORegistry.class);
        }

        // Command
        {
            context.registerService(CommandError.class.getName(),
                new CommandError(context), Ut.Bnd.command("estore"));
        }
        Ut.Log.bundle(this.getClass())
            .info(MessageOfLog.BUNDLE.START, bundle.getSymbolicName());
    }
}
