package io.zerows.core.feature.database.jooq.exception;

import io.horizon.exception.BootingException;

public class JooqClassInvalidException extends BootingException {
    public JooqClassInvalidException(final Class<?> clazz, final String name) {
        super(clazz, name);
    }

    @Override
    public int getCode() {
        return -40066;
    }
}
