package com.dutact.web.common.auth.jwt;

public interface JWTProcessor {
    JWTBuilder getBuilder();

    /**
     * @throws InvalidJWTException if token is invalid
     */
    VerifiedJWT getVerifiedJWT(String token);
}
