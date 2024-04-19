package io.zerows.core.metadata.exception.boot;

import io.horizon.exception.BootingException;

public class ConfigKeyMissingException extends BootingException {

    public ConfigKeyMissingException(final Class<?> clazz,
                                     final String key) {
        super(clazz, key);
    }

    @Override
    public int getCode() {
        return -40020;
    }
}
