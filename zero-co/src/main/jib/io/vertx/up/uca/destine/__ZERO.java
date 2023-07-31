package io.vertx.up.uca.destine;

import io.horizon.annotations.Memory;
import io.horizon.uca.cache.Cc;


@SuppressWarnings("all")
interface Pool {
    /**
     * {@link Hymn} 组件专用池
     */
    @Memory(Hymn.class)
    Cc<String, Hymn> CCT_HYMN = Cc.openThread();
}
