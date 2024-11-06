package com.dutact.web.auth.factors;


import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.NewPasswordDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.AccountNotEnabledException;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.auth.exception.NoPermissionException;

public interface AccountService {
    ResponseToken login(LoginDto loginDto) throws InvalidLoginCredentialsException, AccountNotEnabledException;

    void changePassword(Integer accountId, NewPasswordDto newPasswordDto) throws InvalidLoginCredentialsException, NoPermissionException;
}
