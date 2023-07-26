package io.vertx.up.atom.shape;

import io.horizon.uca.log.Annal;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.exception.web._412IndentParsingException;
import io.vertx.up.exception.web._412IndentUnknownException;
import io.vertx.up.fn.Fn;
import io.vertx.up.util.Ut;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

/**
 * 连接配置，位于CRUD模型配置的核心节点 `connect` 的数据结构，结构如下
 * <pre><code>
 * {
 *     "connect": {
 *         "reference": {@link KPoint},
 *         "source": {@link KPoint},
 *         "targetIndent": "提取 identifier 的专用属性名",
 *         "target": {
 *             "identifier1": {@link KPoint},
 *             "identifier2": {@link KPoint}
 *         }
 *     }
 * }
 * </code></pre>
 * 两种模式的 Join 对应的数据结构：
 * <pre><code>
 *     1. 父从表模式
 *        {
 *            "reference",
 *            "source"
 *        }
 *     2. 父主表模式
 *        {
 *            "source",
 *            "targetIndent",
 *            "target"
 *        }
 * </code></pre>
 *
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public class KJoin implements Serializable {

    private static final Annal LOGGER = Annal.get(KJoin.class);
    /** 做JOIN的目标模型的 identifier 属性 */
    private volatile String targetIndent;

    /** 做JOIN的源相关配置，{@link KPoint} */
    private KPoint source;

    /** 做JOIN的主从表模式下的主从表，{@link KPoint} */
    private KPoint reference;

    /** 做JOIN的目标模型（多个）对应的 {@link KPoint} 对应哈希表配置 **/
    private ConcurrentMap<String, KPoint> target = new ConcurrentHashMap<>();

    public String getTargetIndent() {
        return this.targetIndent;
    }

    public void setTargetIndent(final String targetIndent) {
        this.targetIndent = targetIndent;
    }

    public KPoint getSource() {
        return this.source;
    }

    public void setSource(final KPoint source) {
        this.source = source;
    }

    public KPoint getReference() {
        return this.reference;
    }

    public void setReference(final KPoint reference) {
        this.reference = reference;
    }

    public ConcurrentMap<String, KPoint> getTarget() {
        return this.target;
    }

    public void setTarget(final ConcurrentMap<String, KPoint> target) {
        this.target = target;
    }

    // --------------------- 非POJO类方法 ---------------------
    // 连接点

    /**
     * 从数据中解析出 identifier，直接根据传入 JSON 数据解析出模型标识符 identifier
     * 解析步骤如下：
     * <pre><code>
     *     1. 检查是否存在 `targetIndent` 属性（解析数据时必须）
     *     2. - 若存在 `targetIndent` 中定义的属性，则从数据中提取 data[targetIndent]
     *        - 若不存在 `targetIndent` 中定义的属性，则直接使用 `targetIndent` 作为 identifier（固定常量）
     * </code></pre>
     *
     * @param data {@link JsonObject} 传入的数据
     *
     * @return {@link String} 解析出的 identifier
     */
    private String identifier(final JsonObject data) {
        if (Ut.isNil(this.targetIndent)) {
            LOGGER.warn("The `targetIndent` field is null");
            return null;
        }
        final String identifier;
        if (data.containsKey(this.targetIndent)) {
            // 解析值
            identifier = data.getString(this.targetIndent);
        } else {
            // 固定值
            identifier = this.targetIndent;
        }
        return identifier;
    }


    /**
     * 从数据中提取连接点配置，这种模式主要应用于主从表模式，即：
     * 一个主表对应多个从表时，需要从主表数据中提取从表的 identifier 模型标识符，有了
     * 此标识符才可以让主表模型和从表模型执行 JOIN 操作。
     *
     * @param data {@link JsonObject} 传入的数据
     *
     * @return {@link KPoint} 连接点
     */
    public KPoint point(final JsonObject data) {
        final String identifier = this.identifier(data);
        Fn.out(Ut.isNil(identifier), _412IndentParsingException.class, this.getClass(), this.targetIndent, data);
        final KPoint result = this.point(identifier);
        if (Objects.isNull(result)) {
            LOGGER.info("System could not find configuration for `{0}` with data = {1}", identifier, data.encode());
        }
        return result;
    }

    /**
     * 从配置中直接提取连接点配置，根据传入的 identifier 执行提取，对应：
     * <pre><code>
     *     {
     *         "target": {
     *             "identifier1": {@link KPoint},
     *             "identifier2": {@link KPoint}
     *         }
     *     }
     * </code></pre>
     *
     * @param identifier 统一模型标识符
     *
     * @return {@link KPoint} 连接点
     */
    public KPoint point(final String identifier) {
        final KPoint target = this.target.getOrDefault(identifier, null);
        if (Objects.isNull(target)) {
            return null;
        }
        return target.indent(identifier);
    }


    /**
     * 从数据中提取连接点配置（批量版）
     *
     * @param data {@link JsonArray} 传入的数据
     *
     * @return {@link KPoint} 连接点
     */
    public KPoint point(final JsonArray data) {
        final Set<String> idSet = new HashSet<>();
        Ut.itJArray(data).map(this::identifier).filter(Objects::nonNull).forEach(idSet::add);
        Fn.out(1 != idSet.size(), _412IndentUnknownException.class, this.getClass(), this.targetIndent);
        final String identifier = idSet.iterator().next();
        return this.point(identifier);
    }

    
    // 连接条件方法

    /**
     * key -> keyJoin
     */
    public void dataIn(final JsonObject ds, final KPoint target, final JsonObject data) {
        this.dataRun(target, (source) -> {
            final String joinedValue = ds.getString(source.getKey());
            if (Ut.isNotNil(joinedValue)) {
                data.put(target.getKeyJoin(), joinedValue);
            }
        });
    }

    public void dataCond(final JsonObject ds, final KPoint target, final JsonObject data) {
        this.dataRun(target, (source) -> {
            String joinedValue = ds.getString(source.getKey());
            if (Ut.isNil(joinedValue)) {
                joinedValue = ds.getString(target.getKeyJoin());
            }
            if (Ut.isNotNil(joinedValue)) {
                data.put(target.getKeyJoin(), joinedValue);
            }
        });
    }

    /**
     * keyJoin -> key
     */
    public void dataOut(final JsonObject ds, final KPoint target, final JsonObject data) {
        this.dataRun(target, (source) -> {
            final String joinedValue = ds.getString(target.getKeyJoin());
            if (Ut.isNotNil(joinedValue)) {
                data.put(source.getKey(), joinedValue);
            }
        });
    }

    private void dataRun(final KPoint target, final Consumer<KPoint> consumer) {
        final KPoint source = this.source;
        if (Objects.nonNull(target) && Objects.nonNull(source)) {
            consumer.accept(source);
        }
    }
}
