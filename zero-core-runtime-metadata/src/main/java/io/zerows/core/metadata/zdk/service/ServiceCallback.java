package io.zerows.core.metadata.zdk.service;

import io.vertx.up.util.Ut;
import io.zerows.core.metadata.uca.logging.OLog;
import org.osgi.framework.Bundle;

/**
 * @author lang : 2024-03-25
 */
public interface ServiceCallback<T> extends ServiceMonitor {

    void start(T service, Bundle bundle);

    void stop(T service, Bundle bundle);

    @Override
    default OLog logger() {
        return Ut.Log.callback(this.getClass());
    }
}
