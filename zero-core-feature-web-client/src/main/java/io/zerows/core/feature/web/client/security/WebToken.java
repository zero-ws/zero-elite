package io.zerows.core.feature.web.client.security;

/*
 * Token core interface
 */
public interface WebToken {
    /*
     * Read token string
     */
    String token();

    /*
     * Generate `Authorization` header value
     */
    String authorization();
}
