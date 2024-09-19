package com.dutact.web.auth.factors.ctsv;

import com.dutact.web.auth.UserCredential;
import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.auth.token.jwt.JWTProcessor;
import com.dutact.web.core.repositories.CTSVRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CTSVAuthServiceImpl implements CTSVAuthService {
    private final CTSVRepository ctsvRepository;

    private final JWTProcessor jwtProcessor;

    private final PasswordEncoder passwordEncoder;

    private final long verificationDuration;

    public CTSVAuthServiceImpl(@Value("${auth.jwt.verification-duration}") long verificationDuration,
                               JWTProcessor jwtProcessor,
                               CTSVRepository ctsvRepository,
                               PasswordEncoder passwordEncoder) {
        this.verificationDuration = verificationDuration;
        this.ctsvRepository = ctsvRepository;
        this.jwtProcessor = jwtProcessor;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseToken login(LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        UserCredential userCredential = ctsvRepository.findByUsername(username)
                .orElseThrow(InvalidLoginCredentialsException::new);

        if (!passwordEncoder.matches(password, userCredential.getPassword())) {
            throw new InvalidLoginCredentialsException();
        }

        String token = jwtProcessor.getBuilder()
                .withSubject(username)
                .withClaim("expiredAt", System.currentTimeMillis() + verificationDuration)
                .withScopes(List.of("ROLE_"+ userCredential.getRole()))
                .build();

        ResponseToken responseToken = new ResponseToken();
        responseToken.setAccessToken(token);

        return responseToken;
    }
}
