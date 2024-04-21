package io.zerows.core.configuration.store;

import io.macrocosm.specification.config.HSetting;
import io.zerows.core.configuration.atom.NodeNetwork;
import io.zerows.core.configuration.uca.setup.CommonProcessor;
import io.zerows.core.configuration.zdk.Processor;
import io.zerows.core.metadata.store.OZeroStore;

/**
 * @author lang : 2024-04-20
 */
public class ORepositoryOption {

    public static void configure() {
        // 构造缓存基础数据
        final OCacheNode cache = OCacheNode.of();
        final NodeNetwork network = cache.network();

        // 构造处理器
        final Processor<NodeNetwork, HSetting> processor = CommonProcessor.of();
        final HSetting setting = OZeroStore.setting();
        processor.makeup(network, setting);
    }
}