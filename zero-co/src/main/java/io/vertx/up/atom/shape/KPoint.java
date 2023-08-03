package io.vertx.up.atom.shape;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.horizon.atom.datamation.KMapping;
import io.modello.eon.em.EmModel;
import io.vertx.core.json.JsonObject;
import io.vertx.up.exception.web._409JoinTargetException;
import io.vertx.up.fn.Fn;
import io.vertx.up.util.Ut;
import io.zerows.jackson.databind.ClassDeserializer;
import io.zerows.jackson.databind.ClassSerializer;
import io.zerows.jackson.databind.JsonObjectDeserializer;
import io.zerows.jackson.databind.JsonObjectSerializer;

import java.io.Serializable;
import java.util.Objects;

/**
 * 连接点专用配置，所有连接点配置一致 {@link KPoint} ，都是固定数据结构：
 * <pre><code>
 * {
 *     "identifier": "连接点的唯一标识符，通常是模型名",
 *     "crud": "{@link EmModel.Join#CRUD} 模式必须：crud join 模式必须的，使用 CURD 标准定义执行模式连接",
 *     "classDao": "{@link EmModel.Join#DAO} 模式必须：直连模式",
 *     "classDefine": "{@link EmModel.Join#DEFINE} 模式必须：定义模式",
 *     "key": "当前模型的主键是什么",
 *     "keyJoin": "当前模型的的连接键是什么",
 *     "synonym": {
 *         "field": "field alias"
 *     }
 * }
 * </code></pre>
 * 上述连接点专用属性 synonym 需要说明一下，当两个表连接时，如果两个表中存在相同的字段名，那么在连接时会出现冲突，所以需要
 * 使用此属性重新定义某张表中的属性别名，保证连接时不会冲突。
 *
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public class KPoint implements Serializable {
    /** 模型标识符 */
    @JsonIgnore
    private String identifier;


    /** {@link EmModel.Join#CRUD} 专用：可解析的crud连接专用文件 */
    private String crud;


    /** {@link EmModel.Join#DAO} 专用：Java的类名，做直连JOIN专用 */
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> classDao;


    /** {@link EmModel.Join#DEFINE} 专用： **/
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> classDefine;


    /** 主键名 **/
    private String key;

    /** 连接键名 **/
    private String keyJoin;

    @JsonSerialize(using = JsonObjectSerializer.class)
    @JsonDeserialize(using = JsonObjectDeserializer.class)
    private JsonObject synonym;

    public String getCrud() {
        return this.crud;
    }

    public void setCrud(final String crud) {
        this.crud = crud;
    }

    public String getKey() {
        return Ut.isNil(this.key) ? "key" : this.key;
    }

    public void setKey(final String key) {
        if (Ut.isNotNil(key)) {
            this.key = key;
        }
    }

    public String getKeyJoin() {
        return this.keyJoin;
    }

    public void setKeyJoin(final String keyJoin) {
        this.keyJoin = keyJoin;
    }

    public Class<?> getClassDao() {
        return this.classDao;
    }

    public void setClassDao(final Class<?> classDao) {
        this.classDao = classDao;
    }

    public Class<?> getClassDefine() {
        return this.classDefine;
    }

    public void setClassDefine(final Class<?> classDefine) {
        this.classDefine = classDefine;
    }

    public JsonObject getSynonym() {
        return Objects.isNull(this.synonym) ? new JsonObject() : this.synonym;
    }

    public void setSynonym(final JsonObject synonym) {
        this.synonym = synonym;
    }

    public KMapping synonym() {
        return new KMapping(this.synonym);
    }


    // ------------------ 模式解析

    /**
     * 获取目标JOIN模式的相关配置，主要有三种模式，按优先级排序
     *
     * @return {@link EmModel.Join}
     */
    public EmModel.Join modeTarget() {
        /* P1: CRUD */
        if (Ut.isNotNil(this.crud)) {
            return EmModel.Join.CRUD;
        }
        /* P2: classDao */
        if (Objects.nonNull(this.classDao)) {
            return EmModel.Join.DAO;
        }
        /* P3: classDefine also null, throw error out. */
        Fn.out(Objects.isNull(this.classDefine), _409JoinTargetException.class, this.getClass());
        return EmModel.Join.DEFINE;
    }

    /**
     * 获取源模式，元模式不支持 CRUD 配置，只支持两种模式
     *
     * @return {@link EmModel.Join}
     */
    public EmModel.Join modeSource() {
        /* P1: classDao */
        if (Objects.nonNull(this.classDao)) {
            return EmModel.Join.DAO;
        }
        /* P2: classDefine */
        if (Objects.nonNull(this.classDefine)) {
            return EmModel.Join.DEFINE;
        }
        /* P3: keyJoin */
        Fn.out(Ut.isNil(this.keyJoin), _409JoinTargetException.class, this.getClass());
        return EmModel.Join.CRUD;
    }

    /**
     * 设置当前连接点的模型标识符
     *
     * @param identifier 模型标识符
     *
     * @return {@link KPoint}
     */
    public KPoint indent(final String identifier) {
        this.identifier = identifier;
        if (Objects.isNull(this.crud)) {
            // Default Applying
            this.crud = identifier;
        }
        return this;
    }

    /**
     * 读取模型标识符
     *
     * @return 模型标识符
     */
    public String indent() {
        return this.identifier;
    }

    @Override
    public String toString() {
        return "KPoint{" +
            "identifier='" + this.identifier + '\'' +
            ", crud='" + this.crud + '\'' +
            ", classDao=" + this.classDao +
            ", classDefine=" + this.classDefine +
            ", key='" + this.key + '\'' +
            ", keyJoin='" + this.keyJoin + '\'' +
            ", synonym=" + this.synonym +
            '}';
    }
}
