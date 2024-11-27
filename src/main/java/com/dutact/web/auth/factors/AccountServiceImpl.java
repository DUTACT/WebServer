package com.dutact.web.auth.factors;

import com.dutact.web.auth.context.SecurityContextUtils;
import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.NewPasswordDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.AccountNotEnabledException;
import com.dutact.web.auth.exception.InvalidCredentialsException;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.auth.exception.NoPermissionException;
import com.dutact.web.auth.token.jwt.JWTBuilder;
import com.dutact.web.auth.token.jwt.JWTProcessor;
import com.dutact.web.core.entities.Admin;
import com.dutact.web.core.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private final JWTProcessor jwtProcessor;
    private final StudentAccountService studentAccountService;
    private final OrganizerAccountService organizerAccountService;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    private final long jwtLifespan;

    public AccountServiceImpl(@Value("${auth.jwt.lifespan}") long jwtLifespan,
                              JWTProcessor jwtProcessor,
                              AccountRepository accountRepository,
                              StudentAccountService studentAccountService,
                              OrganizerAccountService organizerAccountService,
                              AdminRepository adminRepository,
                              PasswordEncoder passwordEncoder) {
        this.jwtLifespan = jwtLifespan;
        this.accountRepository = accountRepository;
        this.jwtProcessor = jwtProcessor;
        this.studentAccountService = studentAccountService;
        this.organizerAccountService = organizerAccountService;
        this.adminRepository = adminRepository;
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
        JWTBuilder tokenBuilder = jwtProcessor.getBuilder()
                .withSubject(username)
                .withClaim("expiredAt", System.currentTimeMillis() + jwtLifespan)
                .withScopes(List.of("ROLE_" + account.getRole()));

        if (account.getRole().equals(Role.STUDENT)) {
            tokenBuilder.withClaim("studentId", studentAccountService.getStudentId(username).orElseThrow());
        } else if (account.getRole().equals(Role.EVENT_ORGANIZER) || account.getRole().equals(Role.STUDENT_AFFAIRS_OFFICE)) {
            tokenBuilder.withClaim("organizerId", organizerAccountService.getOrganizerId(username).orElseThrow());
        }

        String token = tokenBuilder.build();

        ResponseToken responseToken = new ResponseToken();
        responseToken.setAccessToken(token);

        return responseToken;
    }

    @Override
    public void changePassword(Integer accountId, NewPasswordDto newPasswordDto) throws NoPermissionException, InvalidCredentialsException {
        String username = SecurityContextUtils.getUsername();
        boolean isAdmin = isLoginUserAdmin();

        Account account = accountRepository.findByUsername(username).orElseThrow();
        if (!isAdmin && !account.getId().equals(accountId)) {
            throw new NoPermissionException();
        }
        if (!passwordEncoder.matches(newPasswordDto.getOldPassword(), account.getPassword())) {
            throw new InvalidCredentialsException();
        }
        account.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
        accountRepository.save(account);
    }

    @Override
    public Integer getAccountIdByToken(String token) {
        var username = jwtProcessor.getVerifiedJWT(token).getUsername();
        return accountRepository
                .findByUsername(username).map(Account::getId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    }

    @Override
    public Integer getAccountIdByUsername(String username) {
        return accountRepository
                .findByUsername(username).map(Account::getId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));
    }

    private boolean isLoginUserAdmin() {
        Optional<Admin> admin = adminRepository.findByUsername(SecurityContextUtils.getUsername());
        return admin.isPresent();
    }
}
