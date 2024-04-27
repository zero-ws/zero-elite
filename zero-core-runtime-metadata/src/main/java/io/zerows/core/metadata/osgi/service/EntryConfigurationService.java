package io.zerows.core.metadata.osgi.service;

import io.horizon.uca.cache.Cc;
import io.macrocosm.specification.config.HConfig;
import io.macrocosm.specification.config.HSetting;
import io.zerows.core.metadata.store.OZeroEquip;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import java.util.Objects;

/**
 * @author lang : 2024-04-27
 */
public class EntryConfigurationService implements EntryConfiguration {
    /**
     * 针对平台配置每个 Bundle 只允许有一个对应配置信息，即一套信息，其配置加载流程可以在后期对
     * {@link OZeroEquip} 的初始化流程执行更改，以强化不同内容相关配置信息
     * <pre><code>
     *     1. Platform：单机版专用
     *     2. App：应用入口
     *     3. Bundle：插件入口
     * </code></pre>
     */
    private static final Cc<Long, HSetting> CC_SETTING = Cc.open();

    @Override
    public HSetting platform(final Bundle caller) {
        Objects.requireNonNull(caller);
        return CC_SETTING.pick(() -> OZeroEquip.of(caller).initialize(), caller.getBundleId());
    }

    @Override
    public HConfig plugin(final String optionKey) {
        // 此处调用服务时，直接读取当前 Bundle，只提取当前 Bundle 对应配置信息
        final Bundle bundle = FrameworkUtil.getBundle(this.getClass());
        Objects.requireNonNull(bundle);
        final HSetting setting = this.platform(bundle);
        this.logger().info("The Bundle: {0} is loading configuration: key = {1}",
            bundle.getSymbolicName(), optionKey);
        return setting.infix(optionKey);
    }
}
