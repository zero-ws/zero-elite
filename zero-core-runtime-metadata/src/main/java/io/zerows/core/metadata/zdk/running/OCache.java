package io.zerows.core.metadata.zdk.running;

import io.vertx.up.annotations.metadata.Semantics;

/**
 * 缓存接口，用来存储全局缓存数据
 *
 * @author lang : 2024-04-17
 */
@Semantics
public interface OCache<T> {

    default <C> OCache<T> configure(final C configuration) {
        return this;
    }

    // ------------ 操作接口 ---------------
    T get();

    OCache<T> add(T t);

    OCache<T> remove(T t);
}
