package io.vertx.up.exception.boot;

import io.horizon.exception.BootingException;

public class WallKeyMissingException extends BootingException {

    public WallKeyMissingException(final Class<?> clazz,
                                   final String key,
                                   final Class<?> target) {
        super(clazz, key, target);
    }

    @Override
    public int getCode() {
        return -40040;
    }
}
