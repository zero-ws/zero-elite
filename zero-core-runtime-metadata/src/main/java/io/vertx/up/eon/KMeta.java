package io.vertx.up.eon;

/**
 * @author lang : 2024-04-17
 */
public interface KMeta {

    enum Typed {
        INFUSION,
        QUEUE,
        ENDPOINT,
        WORKER,
    }

    interface Component {
        String DEFAULT_SCANNED = "Component/Default-HScanned";
    }
}
