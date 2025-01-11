package com.dutact.web.common.auth.jwt;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.List;
import java.util.Map;

public class JWTBuilderImpl implements JWTBuilder {
    private final JWTCreator.Builder builder;

    private final Algorithm algorithm;

    public JWTBuilderImpl(JWTCreator.Builder builder, Algorithm algorithm) {
        this.builder = builder;
        this.algorithm = algorithm;
    }

    @Override
    public JWTBuilder withSubject(String subject) {
        builder.withSubject(subject);
        return this;
    }

    @Override
    public JWTBuilder withScopes(List<String> scope) {
        builder.withClaim("scp", scope);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JWTBuilder withClaim(String key, Object value) {
        if (value instanceof Integer) {
            builder.withClaim(key, (Integer) value);
        } else if (value instanceof Long) {
            builder.withClaim(key, (Long) value);
        } else if (value instanceof Double) {
            builder.withClaim(key, (Double) value);
        } else if (value instanceof String) {
            builder.withClaim(key, (String) value);
        } else if (value instanceof Boolean) {
            builder.withClaim(key, (Boolean) value);
        } else if (value instanceof String[]) {
            builder.withArrayClaim(key, (String[]) value);
        } else if (value instanceof Integer[]) {
            builder.withArrayClaim(key, (Integer[]) value);
        } else if (value instanceof Long[]) {
            builder.withArrayClaim(key, (Long[]) value);
        } else if (value instanceof List) {
            builder.withClaim(key, (List<?>) value);
        } else if (value instanceof Map) {
            try {
                builder.withClaim(key, (Map<String, ?>) value);
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Invalid type of value [" + value.getClass() + "]");
            }
        } else {
            throw new IllegalArgumentException("Invalid type of value [" + value.getClass() + "]");
        }

        return this;
    }

    @Override
    public String build() {
        return builder.sign(algorithm);
    }
}
