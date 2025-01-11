package com.dutact.web.common.auth.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class BearerAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;

    public BearerAuthenticationToken(String token) {
        super(Collections.emptyList());
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
