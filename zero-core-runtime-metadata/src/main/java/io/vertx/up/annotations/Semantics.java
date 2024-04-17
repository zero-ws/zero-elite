package io.vertx.up.annotations;

import io.vertx.up.eon.em.EmMonitor;

import java.lang.annotation.*;

/**
 * 语义注解，系统内部专用，用来标记哪些接口是带有 Bundle / Web 双环境切换的接口，内部扫描时很容易将相关接口直接扫描并进行监控，同时也
 * 对新版底层有一个基础的重构。
 *
 * @author lang : 2024-04-17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Semantics {
    /**
     * 默认组件可统计（健康检查专用），但这种组件也可以直接关闭，默认为双环境接口只要带上了 {@link Semantics} 标记则证明整个接口是需要
     * 被健康检查器扫描到的，且内部分析要知道它的
     * <pre><code>
     *     1. 接口子类类型数量
     *     2. 每种类型数对应的对象数量
     * </code></pre>
     */
    EmMonitor.Type type() default EmMonitor.Type.AMBIGUITY;
}
