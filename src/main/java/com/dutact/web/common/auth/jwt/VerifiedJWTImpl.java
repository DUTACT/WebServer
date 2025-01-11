package com.dutact.web.common.auth.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.List;
import java.util.Optional;

public class VerifiedJWTImpl implements VerifiedJWT {
    private final DecodedJWT decodedJWT;

    public VerifiedJWTImpl(DecodedJWT decodedJWT) {
        this.decodedJWT = decodedJWT;
    }

    @Override
    public String getUsername() {
        return decodedJWT.getSubject();
    }

    @Override
    public <T> Optional<T> getClaim(String claim, Class<T> clazz) {
        return Optional.ofNullable(decodedJWT.getClaim(claim).as(clazz));
    }

    @Override
    public <T> Optional<List<T>> getListClaim(String claim, Class<T> clazz) {
        return Optional.ofNullable(decodedJWT.getClaim(claim).asList(clazz));
    }

    @Override
    public boolean hasClaim(String claim) {
        return !decodedJWT.getClaim(claim).isNull();
    }
}
