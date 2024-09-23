package com.dutact.web.auth.factors.organization;

import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrganizationAuthController {
    private final OrganizationAuthService organizationAuthService;

    public OrganizationAuthController(OrganizationAuthService organizationAuthService) {
        this.organizationAuthService = organizationAuthService;
    }

    @PostMapping("/api/organization/login")
    public ResponseEntity<Object> postLogin(@RequestBody @Valid LoginDto loginDto) {
        try {
            ResponseToken responseToken = organizationAuthService.login(loginDto);
            return ResponseEntity.ok(responseToken);
        }
        catch (InvalidLoginCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }
}
