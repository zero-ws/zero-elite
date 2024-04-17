package io.zerows.core.metadata.zdk.running;

import io.horizon.eon.VString;
import io.horizon.uca.log.Annal;
import io.vertx.up.annotations.metadata.Semantics;
import io.zerows.core.metadata.cache.CStore;

import java.util.Objects;

/**
 * OSGI 专用日志接口，用于基础日志信息，和 OSGI Framework 相关，底层虽然采用了 {@link Annal}，但内置使用第二层用于日志输出
 * <pre><code>
 *     1. {@link Annal} 主要用于在开发过程中输出日志，通常采用类似
 *        Annal LOGGER = Annal.get(XXX.class);
 *        Annal LOGGER = new Log4JAnnal(XXX.class);
 *     2. {@link OLog} 则负责快速日志处理
 *        KLog.of(XXX.class, "name").info("message");
 * </code></pre>
 *
 * @author lang : 2024-04-17
 */
@Semantics
public interface OLog {

    static OLog of(final Class<?> clazz, final String name) {
        Objects.requireNonNull(clazz);
        return CStore.CC_LOG.pick(() -> new LogConsole(clazz, name), clazz.getName() + VString.SLASH + name);
    }

    void info(String pattern, Object... args);

    void debug(String pattern, Object... args);

    void warn(String pattern, Object... args);

    void error(String pattern, Object... args);

    void fatal(Throwable ex);
}
