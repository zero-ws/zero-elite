package io.zerows.core.metadata.cache;

import io.horizon.annotations.Memory;
import io.horizon.specification.typed.TCombiner;
import io.horizon.uca.cache.Cc;

/**
 * @author lang : 2024-04-17
 */
interface CStoreComponent {


    /*
     * 「界面级别处理」
     */
    @SuppressWarnings("all")
    @Memory(TCombiner.class)
    Cc<String, TCombiner> CC_COMBINER = Cc.openThread();
}
