package io.zerows.core.metadata.osgi.command.failure;

import io.horizon.eon.VString;
import io.zerows.core.metadata.osgi.command.CommandInfo;
import io.zerows.core.metadata.zdk.AbstractCommand;
import io.zerows.core.metadata.zdk.running.OCommander;
import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.Parameter;
import org.osgi.framework.BundleContext;

import java.util.Objects;

/**
 * @author lang : 2024-04-17
 */
public class CommandFailure extends AbstractCommand {

    public CommandFailure(final BundleContext context) {
        super(context);
    }

    @Descriptor("Monitor error stored data.")
    public String failure(@Parameter(names = CommandInfo.failure.INFO, absentValue = "") final String info) {
        // 1. 提取组件
        final OCommander commander = FailureEntry.create(info);
        if (Objects.nonNull(commander)) {
            commander.execute(this.context.getBundle());
        }
        return VString.EMPTY;
    }
}
