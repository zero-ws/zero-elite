package io.zerows.core.metadata.uca.callback;

import io.zerows.core.metadata.zdk.running.ORegistry;
import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-17
 */
public class SettingCallback {

    private final Bundle bundle;

    public SettingCallback(final Bundle bundle) {
        this.bundle = bundle;
    }

    public void start(final ORegistry registry) {
        // 注册服务
        registry.install(this.bundle);
    }

    public void stop(final ORegistry registry) {
        // 注销服务
        registry.uninstall(this.bundle);
    }
}
