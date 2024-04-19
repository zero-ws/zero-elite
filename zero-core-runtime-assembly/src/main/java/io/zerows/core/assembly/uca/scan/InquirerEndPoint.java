package io.zerows.core.assembly.uca.scan;

import io.vertx.up.annotations.EndPoint;
import io.zerows.core.metadata.zdk.Inquirer;

import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
public class InquirerEndPoint implements Inquirer<Set<Class<?>>> {

    @Override
    public Set<Class<?>> scan(final Set<Class<?>> clazzes) {
        final Set<Class<?>> endpoints = clazzes.stream()
            .filter((item) -> item.isAnnotationPresent(EndPoint.class))
            .collect(Collectors.toSet());
        this.tracker().info(INFO.ENDPOINT, endpoints.size());
        return endpoints;
    }
}
