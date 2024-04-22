package io.zerows.core.metadata.osgi.command;

import io.horizon.eon.VString;
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

    /**
     * 命令格式
     * <pre><code>
     *     failure store <value>
     *     - class：扫描的类
     *     - error：错误和存储异常信息
     * </code></pre>
     *
     * @param type 类型
     *
     * @return 返回值
     */
    @Descriptor("Monitor error stored data.")
    public String failure(@Parameter(names = OCommand.store.TYPE, absentValue = "") final String type) {
        // 1. 提取组件
        final OCommander commander = CommandFactory.create(type);
        if (Objects.nonNull(commander)) {
            commander.execute(this.context.getBundle());
        }
        return VString.EMPTY;
    }
}
