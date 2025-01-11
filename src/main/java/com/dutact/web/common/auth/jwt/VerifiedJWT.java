package com.dutact.web.common.auth.jwt;

import com.dutact.web.common.auth.UserPrincipal;

import java.util.List;
import java.util.Optional;

public interface VerifiedJWT extends UserPrincipal {
    <T> Optional<T> getClaim(String claim, Class<T> clazz);

    <T> Optional<List<T>> getListClaim(String claim, Class<T> clazz);

    boolean hasClaim(String claim);
}
