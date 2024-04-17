package io.zerows.core.metadata.zdk.service;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import java.util.function.Supplier;

/**
 * 统一提供带有 Dependency 的服务接口，用于分析服务依赖专用处理
 *
 * @author lang : 2024-03-25
 */
public interface ServiceConnector extends ServiceMonitor {

    default void serviceDependency(final DependencyManager dm, final Supplier<Component> supplier,
                                   final Supplier<ServiceDependency> dependencySupplier) {
    }

    void serviceRegister(DependencyManager dm, Supplier<Component> supplier);
}
