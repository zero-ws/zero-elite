package io.zerows.core.metadata.osgi.command;

import io.vertx.up.util.Ut;
import io.zerows.core.metadata.zdk.running.OCommander;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lang : 2024-04-22
 */
class FailureEntry {

    private static final ConcurrentMap<String, OCommander> COMMAND_STORE = new ConcurrentHashMap<>() {
        {
            this.put("all", new FailureAll());
            this.put("size", new FailureSize());
        }
    };

    static OCommander create(final String value) {
        if (!COMMAND_STORE.containsKey(value)) {
            System.out.println("(E) System could not find the component of :" + value);
            System.out.println("(E) Valid commands is: " + Ut.fromJoin(COMMAND_STORE.keySet()));
        }
        System.out.println("[ ZERO ] Command : " + value);
        return COMMAND_STORE.get(value);
    }
}
