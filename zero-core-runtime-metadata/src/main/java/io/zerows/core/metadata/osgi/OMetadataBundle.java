package io.zerows.metadata.osgi;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.annotation.bundle.Header;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * @author lang : 2024-04-17
 */
@Header(name = Constants.BUNDLE_ACTIVATOR, value = "${@class}")
public class OMetadataBundle extends DependencyActivatorBase {
    @Override
    public void init(final BundleContext bundleContext, final DependencyManager dependencyManager) throws Exception {

    }
}
