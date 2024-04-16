package io.vertx.up.uca.options;

import io.horizon.exception.ProgramException;

public interface Visitor<T> {
    T visit(String... keys) throws ProgramException;
}
