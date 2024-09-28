package com.dutact.web.auth.factors;


import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.AccountNotEnabledException;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;

public interface AccountService {
    ResponseToken login(LoginDto loginDto) throws InvalidLoginCredentialsException, AccountNotEnabledException;
}
