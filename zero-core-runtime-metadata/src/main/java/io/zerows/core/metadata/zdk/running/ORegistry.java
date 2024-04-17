package io.zerows.core.metadata.zdk.running;

import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-04-17
 */
public interface ORegistry {

    void install(Bundle bundle);

    void uninstall(Bundle bundle);
}
