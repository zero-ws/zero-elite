package io.zerows.core.metadata.cache;

import io.vertx.up.annotations.Semantics;

/**
 * @author lang : 2024-04-17
 */
@Semantics
public interface CStore extends
    CStoreObject,           // 语言级对象
    CStoreComponent,        // 高阶接口对象
    CStoreTrack             // 跟踪专用对象：日志、监控等
{
}
