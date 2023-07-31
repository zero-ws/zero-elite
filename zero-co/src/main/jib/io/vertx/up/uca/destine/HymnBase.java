package io.vertx.up.uca.destine;

import io.vertx.up.atom.shape.KJoin;

/**
 * 专用抽象类，实际处理过程中会包含内置的 {@link io.vertx.up.atom.shape.KJoin} 的引用，根据实际定义的连接点元数据信息来解析连接点专用，此处可实现多种不同模式的连接流程，以完善连接点的解析过程。
 *
 * @author lang : 2023-07-28
 */
public abstract class HymnBase<T> implements Hymn<T> {
    /**
     * 构造此对象时，关联的 {@link KJoin} 引用，针对线程级的对象构造，此引用会存储在当前对象中，最终形成 {@link Hymn} 的线程化对象以保持完整对象引用。
     */
    protected final transient KJoin joinRef;

    protected HymnBase(final KJoin joinRef) {
        this.joinRef = joinRef;
    }
}
