package com.dutact.web.auth.factors.admin;


import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;

public interface AdminAuthService {
    ResponseToken login(LoginDto loginDto);
}
