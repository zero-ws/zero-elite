package io.zerows.macro.plugin.booting;


import io.horizon.eon.spec.VBoot;
import io.horizon.uca.cache.Cc;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.util.Ut;
import io.zerows.core.metadata.store.config.OZeroStore;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

interface Pool {
    Cc<Class<?>, HExtension> CC_BOOTS = Cc.open();
}

/**
 * 扩展启动器，用于 Zero Extension 扩展模块专用，职责如下：
 * <pre><code>
 *     1. 负责扩展模块数据导入
 *     2. 负责扩展模块抓取 crud 部分配置
 * </code></pre>
 *
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public interface HExtension {

    static Set<HExtension> initialize() {
        /* Boot processing */
        final ConcurrentMap<Class<?>, HExtension> data = Pool.CC_BOOTS.store();
        if (data.isEmpty()) {
            final JsonObject launcher = OZeroStore.launcherJ();
            final JsonArray boots = Ut.valueJArray(launcher, VBoot.EXTENSION);
            Ut.itJArray(boots).forEach(json -> {
                final Class<?> bootCls = Ut.clazz(json.getString(VBoot.extension.EXECUTOR), null);
                if (Objects.nonNull(bootCls)) {
                    Pool.CC_BOOTS.pick(() -> Ut.instance(bootCls), bootCls);
                }
            });
        }
        return new HashSet<>(data.values());
    }

    /*
     *  Following two methods are for data loading
     *  - First one is used by Excel Loader  ( zero-ifx-excel )
     *  - The second has been used by BtBoot ( zero-ke )
     */
    ConcurrentMap<String, KConnect> configure();

    List<String> oob();

    List<String> oob(String prefix);

    /*
     * Following two methods are for Crud Default Value
     *  - First has been used by CRUD Extension ( zero-crud )
     *  - Second has been used by UI Extension  ( zero-ui )
     * They are not for data loading but for runtime usage
     */
    ConcurrentMap<String, JsonObject> module();

    ConcurrentMap<String, JsonArray> column();
}
