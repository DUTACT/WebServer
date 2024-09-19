package com.dutact.web.auth.factors.organization;


import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;

public interface OrganizationAuthService {
    ResponseToken login(LoginDto loginDto);
}
