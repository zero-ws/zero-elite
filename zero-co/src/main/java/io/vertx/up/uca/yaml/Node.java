package io.vertx.up.uca.yaml;

/**
 * Read options and set default values
 *
 * @param <T>
 */
public interface Node<T> {

    T read();
}
