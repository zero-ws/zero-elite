package io.zerows.core.configuration.uca.setup;

import io.horizon.uca.cache.Cc;
import io.macrocosm.specification.config.HSetting;
import io.zerows.core.configuration.atom.NodeNetwork;
import io.zerows.core.configuration.zdk.Processor;

/**
 * 通用处理器，调用各个子处理器
 *
 * @author lang : 2024-04-20
 */
public class CommonProcessor implements Processor<NodeNetwork> {

    private static final Cc<String, Processor<NodeNetwork>> CC_PROCESSOR = Cc.openThread();
    private final transient Processor<NodeNetwork> vertxProcessor;

    private CommonProcessor() {
        this.vertxProcessor = new VertxProcessor();
    }

    public static Processor<NodeNetwork> of() {
        return CC_PROCESSOR.pick(CommonProcessor::new, CommonProcessor.class.getName());
    }

    @Override
    public void makeup(final NodeNetwork target, final HSetting setting) {
        // 1. Vertx实例构造处理器
        this.vertxProcessor.makeup(target, setting);
    }
}
