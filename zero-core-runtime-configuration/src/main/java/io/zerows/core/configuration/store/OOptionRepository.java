package io.zerows.core.configuration.store;

import io.macrocosm.specification.config.HSetting;
import io.zerows.core.configuration.atom.NodeNetwork;
import io.zerows.core.configuration.uca.setup.CommonProcessor;
import io.zerows.core.configuration.zdk.Processor;
import io.zerows.core.metadata.store.config.OZeroStore;

/**
 * @author lang : 2024-04-20
 */
public class OOptionRepository {

    public static void configure() {
        // 构造缓存基础数据
        final NodeNetwork network = new NodeNetwork();

        // 构造处理器
        final Processor<NodeNetwork, HSetting> processor = CommonProcessor.of();
        final HSetting setting = OZeroStore.setting();
        processor.makeup(network, setting);


        // 处理完成之后和缓存绑定
        final ONodeCache cache = ONodeCache.of();
        cache.configure(network);
    }
}