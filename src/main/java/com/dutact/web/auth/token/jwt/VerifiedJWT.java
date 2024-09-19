package com.dutact.web.auth.token.jwt;

import java.util.List;
import java.util.Optional;

public interface VerifiedJWT {
    String getUsername();

    <T> Optional<T> getClaim(String claim, Class<T> clazz);

    <T> Optional<List<T>> getListClaim(String claim, Class<T> clazz);

    boolean hasClaim(String claim);
}
