package com.dutact.web.auth.factors.ctsv;


import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;

public interface CTSVAuthService {
    ResponseToken login(LoginDto loginDto) throws InvalidLoginCredentialsException;
}
