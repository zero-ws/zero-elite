package io.zerows.core.configuration.zdk;

import io.horizon.exception.ProgramException;

public interface Visitor<T> {
    T visit(String... keys) throws ProgramException;
}
