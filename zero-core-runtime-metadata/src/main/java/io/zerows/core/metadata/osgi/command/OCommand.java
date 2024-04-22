package io.zerows.core.metadata.osgi.command;

/**
 * @author lang : 2024-04-17
 */
public interface OCommand {

    String STORE = "store";
    String FAILURE = "failure";

    interface store {
        String TYPE = "type";
        String DOMAIN = "domain";
    }

    /**
     * 命令格式如
     * <pre><code>
     *     - failure info all：枚举所有错误信息
     *     - failure size：目前环境中错误的数量
     * </code></pre>
     */
    interface failure {
        // failure info ???
        String INFO = "info";
    }
}
