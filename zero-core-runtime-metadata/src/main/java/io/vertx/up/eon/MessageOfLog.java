package io.vertx.up.eon;

/**
 * @author lang : 2024-04-17
 */
public interface MessageOfLog {
    interface SERVICE {
        String REGISTER = "The service \"{0}\" of \"{1}\" has been registered successfully!";
        String UNREGISTER = "The service \"{0}\" has been unregistered successfully!";
    }

    interface COMMAND {
        String REGISTER = "The command \"{0}\" is Ok for execute!";
        String UNREGISTER = "The command \"{0}\" will be Invalid!";
    }

    interface BUNDLE {
        String START = "The bundle 「{0}」 has been started successfully!";
        String STOP = "The bundle 「{0}」 has been stopped successfully!";
    }
}
