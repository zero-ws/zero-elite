package io.zerows.core.metadata.osgi;

import io.zerows.core.metadata.eon.MessageOfMeta;
import io.zerows.core.metadata.osgi.service.ExceptionDesk;
import io.zerows.core.metadata.osgi.service.ExceptionDeskService;
import io.zerows.core.metadata.zdk.service.AbstractServiceConnector;
import io.zerows.core.metadata.zdk.service.ServiceConnector;
import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.Bundle;

import java.util.function.Supplier;

/**
 * @author lang : 2024-04-22
 */
class CoreMetadataDependency extends AbstractServiceConnector {
    private CoreMetadataDependency(final Bundle bundle) {
        super(bundle);
    }

    static ServiceConnector of(final Bundle bundle) {
        return of(bundle, CoreMetadataDependency::new);
    }

    @Override
    public void serviceRegister(final DependencyManager dm, final Supplier<Component> supplier) {
        // 异常管理服务
        dm.add(supplier.get()
            .setInterface(ExceptionDesk.class, null)
            .setImplementation(ExceptionDeskService.class)
        );
        this.logger().info(MessageOfMeta.Osgi.SERVICE.REGISTER, ExceptionDeskService.class, ExceptionDesk.class);


        // 缓存管理服务
    }
}
