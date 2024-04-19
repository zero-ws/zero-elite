package io.zerows.core.feature.database.jooq.exception;

import io.horizon.exception.BootingException;

public class JooqCondFieldException extends BootingException {

    public JooqCondFieldException(final Class<?> clazz,
                                  final String targetField) {
        super(clazz, targetField);
    }

    @Override
    public int getCode() {
        return -40055;
    }
}
