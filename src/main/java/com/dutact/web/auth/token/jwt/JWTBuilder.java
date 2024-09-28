package com.dutact.web.auth.token.jwt;

import java.util.List;

public interface JWTBuilder {
    JWTBuilder withSubject(String subject);

    JWTBuilder withScopes(List<String> scope);

    JWTBuilder withClaim(String key, Object value);

    String build();
}
