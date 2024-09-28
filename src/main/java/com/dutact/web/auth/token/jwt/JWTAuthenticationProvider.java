package com.dutact.web.auth.token.jwt;

import com.dutact.web.auth.token.BearerAuthenticationToken;
import com.dutact.web.auth.token.InvalidBearerTokenException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;


public class JWTAuthenticationProvider implements AuthenticationProvider {
    private final JWTProcessor jwtProcessor;

    public JWTAuthenticationProvider(JWTProcessor jwtProcessor) {
        this.jwtProcessor = jwtProcessor;

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String token = (String) authentication.getCredentials();
            VerifiedJWT verifiedJWT = jwtProcessor.getVerifiedJWT(token);
            List<GrantedAuthority> authorities = getAuthorities(verifiedJWT);

            return new JWTAuthenticationToken(verifiedJWT, authorities);
        }catch (InvalidJWTException e) {
            throw new InvalidBearerTokenException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private List<GrantedAuthority> getAuthorities(VerifiedJWT verifiedJWT) {
        List<String> authoritiesClaim = verifiedJWT.getListClaim("scp", String.class)
                .orElse(new ArrayList<>());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authoritiesClaim.forEach(authority ->
                authorities.add(new SimpleGrantedAuthority(authority)));

        return authorities;
    }
}
