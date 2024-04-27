package io.zerows.core.metadata.osgi.service;

import io.horizon.spi.boot.HEquip;
import io.macrocosm.specification.config.*;
import io.vertx.up.util.Ut;
import io.zerows.core.metadata.uca.logging.OLog;
import org.osgi.framework.Bundle;

/**
 * 入口配置专用程序，内置返回 {@link HConfig} 的对应配置信息，可根据提供的配置 key 值直接处理相关配置，内部屏蔽单机内容
 * <pre><code>
 *     1. （OSGI）{@link HSetting} 此配置信息为核心主配置，不对外，但可以从接口返回，防止：配置和配置交叉
 *     2. {@link HStation} ( Not for OSGI )
 *     3. （OSGI）{@link HEquip} 此为初始化配置，已经在 OSGI 和非 OSGI 之间实现了配置提取，用来构建设置专用
 *     4. {@link HEnergy} 和 {@link HBoot} 只有在自定义启动器的时候会使用。
 * </code></pre>
 * 配置提取流程，分两种模式：Platform / App 两种
 * <pre><code>
 *     1. Platform
 *        平台模式先读取 conf 中单机应用程序专用的全套配置信息（包含 Infix 和 Extension 对应配置）
 *        若不存在 conf 中提供的内容，则读取 Bundle 内置配置
 *     2. App
 *        应用模式，直接读取 apps/conf 之下的配置信息，结构和单机配置结构一致，但应用会有一个主配置说明
 * </code></pre>
 * 文件系统中的配置为恢复默认专用配置，和 zero-battery 结合到一起之后，所有配置参数会暂存到数据库中（特别是模块配置参数）
 *
 * @author lang : 2024-04-27
 */
public interface EntryConfiguration {
    /**
     * 完整配置：conf 之下配置信息 + Bundle 内置配置
     *
     * @return {@link HSetting}
     */
    HSetting platform(Bundle caller);

    /**
     * 读取单组件相关配置
     *
     * @param optionKey 配置键值
     *
     * @return {@link HConfig}
     */
    HConfig plugin(String optionKey);

    default OLog logger() {
        return Ut.Log.configure(this.getClass());
    }
}
