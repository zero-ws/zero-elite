package io.vertx.up.uca.destine;

import io.horizon.exception.web._501NotSupportException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.atom.shape.KJoin;

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
@SuppressWarnings("all")
public interface Conflate<I, O> {

    // ( JsonArray | JsonObject ) / JsonObject
    static <I> Conflate<I, JsonObject> ofQr(final KJoin joinRef, final boolean isArray) {
        if (isArray) {
            return Pool.CCT_CONFLATE.pick(() -> new ConflateAQr(joinRef));
        } else {
            return Pool.CCT_CONFLATE.pick(() -> new ConflateJQr(joinRef));
        }
    }

    // JsonArray / JsonArray
    static Conflate<JsonArray, JsonArray> ofJArray(final KJoin joinRef, final boolean isOut) {
        if (isOut) {
            return Pool.CCT_CONFLATE.pick(() -> new ConflateAIo(joinRef, true));
        } else {
            return Pool.CCT_CONFLATE.pick(() -> new ConflateAIo(joinRef, false));
        }
    }

    // JsonObject / JsonObject
    static Conflate<JsonObject, JsonObject> ofJObject(final KJoin joinRef, final boolean isOut) {
        if (isOut) {
            return Pool.CCT_CONFLATE.pick(() -> new ConflateJOut(joinRef));
        } else {
            return Pool.CCT_CONFLATE.pick(() -> new ConflateJIn(joinRef));
        }
    }

    // 默认实现是为了 QR 部分量身打造，不可直接使用
    default O treat(final I active, final I assist, final String identifier) {
        throw new _501NotSupportException(this.getClass());
    }

    O treat(final I active, final String identifier);
}
