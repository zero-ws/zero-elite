package io.zerows.core.metadata.uca.callback;

import io.zerows.core.metadata.zdk.service.ServiceInstaller;
import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-17
 */
public class SettingCallback {

    private final Bundle bundle;

    public SettingCallback(final Bundle bundle) {
        this.bundle = bundle;
    }

    public void start(final ServiceInstaller registry) {
        // 注册服务
        registry.install(this.bundle);
    }

    public void stop(final ServiceInstaller registry) {
        // 注销服务
        registry.uninstall(this.bundle);
    }
}
