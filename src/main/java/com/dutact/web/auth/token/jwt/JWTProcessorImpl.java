package com.dutact.web.auth.token.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class JWTProcessorImpl implements JWTProcessor{
    private final Algorithm algorithm;

    private final long tokenLifespanMs;

    public JWTProcessorImpl(Algorithm algorithm, long tokenLifespanMs) {
        this.tokenLifespanMs = tokenLifespanMs;
        this.algorithm = algorithm;
    }

    @Override
    public JWTBuilder getBuilder() {
        long now = System.currentTimeMillis();

        JWTCreator.Builder jwtCreate = JWT.create()
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + tokenLifespanMs));

        return new JWTBuilderImpl(jwtCreate, algorithm);
    }

    @Override
    public VerifiedJWT getVerifiedJWT(String token) {
        try {
            return new VerifiedJWTImpl(JWT.require(algorithm).build().verify(token));
        } catch (JWTVerificationException e) {
            throw new InvalidJWTException(e.getMessage());
        }
    }

}
