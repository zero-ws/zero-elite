package io.zerows.core.metadata.zdk;

import org.osgi.framework.BundleContext;

/**
 * @author lang : 2024-04-17
 */
public abstract class AbstractCommand {
    protected final BundleContext context;

    protected AbstractCommand(final BundleContext context) {
        this.context = context;
    }
}
