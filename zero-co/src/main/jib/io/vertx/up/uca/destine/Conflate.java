package io.vertx.up.uca.destine;

/**
 * 包域，不对外
 * 根据 {@link io.vertx.up.atom.shape.KPoint} 计算对应的组件，处理三个生命周期
 * <pre><code>
 *     1. 输入数据处理
 *     2. 输出数据处理
 *     3. 连接点处理
 * </code></pre>
 * 此处为了处理原版的几个特殊方法。
 *
 * @author lang : 2023-07-30
 */
interface Conflate<I, O> {

    O treat(final I active, final I assist, final String identifier);

    O treat(final I active, final String identifier);
}
