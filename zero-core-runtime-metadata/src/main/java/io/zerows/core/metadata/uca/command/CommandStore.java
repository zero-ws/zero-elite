package io.zerows.core.metadata.uca.command;

import io.horizon.eon.VString;
import io.zerows.core.metadata.eon.OCommand;
import io.zerows.core.metadata.zdk.AbstractCommand;
import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.Parameter;
import org.osgi.framework.BundleContext;

/**
 * @author lang : 2024-04-17
 */
public class CommandStore extends AbstractCommand {

    public CommandStore(final BundleContext context) {
        super(context);
    }

    /**
     * 命令格式
     * <pre><code>
     *     store type xxx
     *     - class：扫描的类
     *     - error：错误和存储异常信息
     * </code></pre>
     *
     * @param type 类型
     *
     * @return 返回值
     */
    @Descriptor("Monitor error stored data.")
    public String store(@Parameter(names = OCommand.Parameter.TYPE, absentValue = "") final String type) {
        System.out.println("Hello " + type);
        return VString.NEW_LINE;
    }
}
