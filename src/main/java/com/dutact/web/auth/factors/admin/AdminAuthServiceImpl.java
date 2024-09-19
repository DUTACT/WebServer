package com.dutact.web.auth.factors.admin;

import com.dutact.web.auth.UserCredential;
import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.auth.token.jwt.JWTProcessor;
import com.dutact.web.core.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {
    private final AdminRepository adminRepository;

    private final JWTProcessor jwtProcessor;

    private final PasswordEncoder passwordEncoder;

    private final long verificationDuration;

    public AdminAuthServiceImpl(@Value("${auth.jwt.verification-duration}") long verificationDuration,
                                JWTProcessor jwtProcessor,
                                AdminRepository adminRepository,
                                PasswordEncoder passwordEncoder) {
        this.verificationDuration = verificationDuration;
        this.adminRepository = adminRepository;
        this.jwtProcessor = jwtProcessor;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseToken login(LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        UserCredential userCredential = adminRepository.findByUsername(username)
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
