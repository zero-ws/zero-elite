package io.zerows.macro.exception.boot;

import io.horizon.exception.BootingException;

public class XtorExecuteException extends BootingException {

    public XtorExecuteException(final Class<?> clazz,
                                final String details) {
        super(clazz, details);
    }

    @Override
    public int getCode() {
        return -40035;
    }
}
