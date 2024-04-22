package io.zerows.core.metadata.osgi.command;

import io.vertx.up.util.Ut;
import io.zerows.core.metadata.zdk.running.OCommander;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lang : 2024-04-22
 */
class CommandFactory {

    private static final ConcurrentMap<String, OCommander> COMMAND_STORE = new ConcurrentHashMap<>() {
        {
            this.put("failure", new CommanderFailure());
        }
    };

    static OCommander create(final String value) {
        if (COMMAND_STORE.containsKey(value)) {
            return COMMAND_STORE.get(value);
        }
        System.out.println("(E) System could not find the component of :" + value);
        System.out.println("(E) Valid commands is: " + Ut.fromJoin(COMMAND_STORE.keySet()));
        return null;
    }
}
