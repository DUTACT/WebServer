package com.dutact.web.auth.factors;

import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.AccountNotEnabledException;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.auth.token.jwt.JWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private final JWTProcessor jwtProcessor;

    private final PasswordEncoder passwordEncoder;

    private final long jwtLifespan;

    public AccountServiceImpl(@Value("${auth.jwt.lifespan}") long jwtLifespan,
                              JWTProcessor jwtProcessor,
                              AccountRepository accountRepository,
                              PasswordEncoder passwordEncoder) {
        this.jwtLifespan = jwtLifespan;
        this.accountRepository = accountRepository;
        this.jwtProcessor = jwtProcessor;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseToken login(LoginDto loginDto) throws InvalidLoginCredentialsException, AccountNotEnabledException {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(InvalidLoginCredentialsException::new);

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new InvalidLoginCredentialsException();
        }

        if (!account.isEnabled()) {
            throw new AccountNotEnabledException();
        }

        String token = jwtProcessor.getBuilder()
                .withSubject(username)
                .withClaim("expiredAt", System.currentTimeMillis() + jwtLifespan)
                .withScopes(List.of("ROLE_"+ account.getRole()))
                .build();

        ResponseToken responseToken = new ResponseToken();
        responseToken.setAccessToken(token);

        return responseToken;
    }
}
