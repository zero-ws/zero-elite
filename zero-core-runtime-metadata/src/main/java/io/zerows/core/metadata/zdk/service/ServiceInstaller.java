package io.zerows.core.metadata.zdk.service;

import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-17
 */
public interface ServiceInstaller extends ServiceMonitor {

    void install(Bundle bundle);

    void uninstall(Bundle bundle);
}
