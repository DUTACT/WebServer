package com.dutact.web.auth.factors.organization;


import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;

public interface OrganizationAuthService {
    ResponseToken login(LoginDto loginDto) throws InvalidLoginCredentialsException;
}
