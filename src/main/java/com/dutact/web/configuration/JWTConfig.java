package com.dutact.web.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import com.dutact.web.common.auth.jwt.JWTProcessor;
import com.dutact.web.common.auth.jwt.JWTProcessorImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class JWTConfig {
    private final String base64EncodedSecretKey;

    private final String algorithm;

    private final long tokenLifespan;

    public JWTConfig(@Value("${auth.jwt.secret}") String base64EncodedSecretKey,
                     @Value("${auth.jwt.algorithm}") String algorithm,
                     @Value("${auth.jwt.lifespan}") long tokenLifespan) {
        this.base64EncodedSecretKey = base64EncodedSecretKey;
        this.algorithm = algorithm;
        this.tokenLifespan = tokenLifespan;
    }

    @Bean
    public JWTProcessor jwtProcessor() {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] secretKey = decoder.decode(base64EncodedSecretKey);
        Algorithm algorithm = getAlgorithm(secretKey);

        return new JWTProcessorImpl(algorithm, tokenLifespan);
    }

    private Algorithm getAlgorithm(byte[] secretKey) {
        return switch (algorithm) {
            case "HS256" -> Algorithm.HMAC256(secretKey);
            case "HS384" -> Algorithm.HMAC384(secretKey);
            case "HS512" -> Algorithm.HMAC512(secretKey);
            default -> throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        };
    }
}
