package com.dutact.web.auth.token.jwt;

import java.util.Map;

public interface JWTProcessor {
    JWTBuilder getBuilder();

    /**
     * @throws InvalidJWTException if token is invalid
     */
    VerifiedJWT getVerifiedJWT(String token);
}
