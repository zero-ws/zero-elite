package io.zerows.core.metadata.uca.callback;

import io.zerows.core.metadata.osgi.service.ExceptionDesk;
import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-17
 */
public class SettingCallback {

    private final Bundle bundle;

    public SettingCallback(final Bundle bundle) {
        this.bundle = bundle;
    }

    public void start(final Object registry) {
        // 注册服务
        if (registry instanceof final ExceptionDesk desk) {
            desk.install(this.bundle);
        }
    }

    public void stop(final Object registry) {
        // 注销服务
        if (registry instanceof final ExceptionDesk desk) {
            desk.uninstall(this.bundle);
        }
    }
}
