package com.dutact.web.common.auth.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {
    private final VerifiedJWT token;

    public JWTAuthenticationToken(VerifiedJWT token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        setAuthenticated(true);
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