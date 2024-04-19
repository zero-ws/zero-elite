package io.zerows.core.feature.database.jooq.exception;

import io.horizon.exception.BootingException;

public class JooqVertxNullException extends BootingException {

    public JooqVertxNullException(
        final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public int getCode() {
        return -40060;
    }
}
