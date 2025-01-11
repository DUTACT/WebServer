package com.dutact.web.common.auth.jwt;

import java.util.List;

public interface JWTBuilder {
    JWTBuilder withSubject(String subject);

    JWTBuilder withScopes(List<String> scope);

    JWTBuilder withClaim(String key, Object value);

    String build();
}
