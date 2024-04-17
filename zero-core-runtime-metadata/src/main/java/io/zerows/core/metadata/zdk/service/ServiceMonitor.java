package io.zerows.core.metadata.zdk.service;

import io.horizon.uca.log.LogModule;
import io.vertx.up.util.Ut;
import io.zerows.core.metadata.uca.logging.OLog;

/**
 * 日志专用接口，暂时只保留日志部分，此接口为顶层接口，可在实现类或子类任何地方直接调用 tracker() 进行日志记录，这种日志记录底层实现
 * 依赖 {@link io.horizon.spi.HorizonIo} 中对接的日志记录器，现阶段版本以 {@link org.slf4j.Logger} 为主，后续版本可直接替换
 * 日志记录器，或对接 ELT 等框架来记录相关日志。
 *
 * @author lang : 2023-07-19
 */
public interface ServiceMonitor {

    /**
     * 日志追踪器，只要实现此接口的方法可以内部调用
     * <pre><code>
     *     this.tracker().xxx
     * </code></pre>
     * 通过这种方式可快速提取日志追踪器
     *
     * @return {@link LogModule}
     */
    default OLog tracker() {
        return Ut.Log.service(this.getClass());
    }
}
