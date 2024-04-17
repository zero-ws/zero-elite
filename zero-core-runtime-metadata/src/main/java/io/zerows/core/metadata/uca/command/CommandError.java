package io.zerows.core.metadata.uca.command;

import io.horizon.eon.VString;
import io.zerows.core.metadata.zdk.AbstractCommand;
import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.Parameter;
import org.osgi.framework.BundleContext;

/**
 * @author lang : 2024-04-17
 */
public class CommandError extends AbstractCommand {

    public CommandError(final BundleContext context) {
        super(context);
    }

    @Descriptor("Monitor error stored data.")
    public String estore(@Parameter(names = "command", absentValue = "") final String command) {
        System.out.println("Hello" + command);
        return VString.NEW_LINE;
    }
}
