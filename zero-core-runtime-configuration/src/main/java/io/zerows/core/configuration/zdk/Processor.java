package io.zerows.core.configuration.zdk;

import io.macrocosm.specification.config.HSetting;
import io.vertx.up.util.Ut;
import io.zerows.core.metadata.uca.logging.OLog;

/**
 * Builder 模式下的执行器，用来执行和处理特定对象专用
 *
 * @author lang : 2024-04-20
 */
public interface Processor<T> {

    void makeup(T target, HSetting setting);

    default OLog tracker() {
        return Ut.Log.configure(this.getClass());
    }
}
