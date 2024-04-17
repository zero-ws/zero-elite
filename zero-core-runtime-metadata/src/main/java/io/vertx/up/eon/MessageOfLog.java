package io.vertx.up.eon;

/**
 * @author lang : 2024-04-17
 */
public interface MessageOfLog {
    interface SERVICE {
        String REGISTER = "The service \"{0}\" of \"{1}\" has been registered successfully!";
        String UNREGISTER = "The service \"{0}\" has been unregistered successfully!";
    }

    interface BUNDLE {
        String START = "The bundle 「{0}」 has been started successfully!";
        String STOP = "The bundle 「{0}」 has been stopped successfully!";
    }
}
