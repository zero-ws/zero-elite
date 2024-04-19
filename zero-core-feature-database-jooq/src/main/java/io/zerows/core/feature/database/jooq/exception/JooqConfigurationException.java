package io.zerows.core.feature.database.jooq.exception;

import io.horizon.exception.BootingException;

public class JooqConfigurationException extends BootingException {

    public JooqConfigurationException(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public int getCode() {
        return -40065;
    }
}
