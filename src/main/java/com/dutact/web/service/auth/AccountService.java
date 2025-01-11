package com.dutact.web.service.auth;


import com.dutact.web.dto.auth.LoginDto;
import com.dutact.web.dto.auth.NewPasswordDto;
import com.dutact.web.dto.auth.ResponseToken;
import com.dutact.web.service.auth.exception.AccountNotEnabledException;
import com.dutact.web.service.auth.exception.InvalidCredentialsException;
import com.dutact.web.service.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.service.auth.exception.NoPermissionException;

public interface AccountService {
    ResponseToken login(LoginDto loginDto) throws InvalidLoginCredentialsException, AccountNotEnabledException;

    void changePassword(Integer accountId, NewPasswordDto newPasswordDto) throws NoPermissionException, InvalidCredentialsException;

    Integer getAccountIdByToken(String token);

    Integer getAccountIdByUsername(String username);
}
