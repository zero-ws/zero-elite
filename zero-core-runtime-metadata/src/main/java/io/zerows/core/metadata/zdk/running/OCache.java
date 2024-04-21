package io.zerows.core.metadata.zdk.running;

import io.horizon.exception.web._501NotSupportException;
import io.vertx.up.annotations.internal.Semantics;

import java.util.Set;

/**
 * 缓存接口，用来存储全局缓存数据
 *
 * @author lang : 2024-04-17
 */
@Semantics
public interface OCache<T> {

    // ------------ 操作接口 ---------------

    default <C> OCache<T> configure(final C configuration) {
        return this;
    }

    default Set<String> keys() {
        throw new _501NotSupportException(this.getClass());
    }

    default T get() {
        throw new _501NotSupportException(this.getClass());
    }

    default T get(final String key) {
        throw new _501NotSupportException(this.getClass());
    }

    OCache<T> add(T t);

    OCache<T> remove(T t);
}
