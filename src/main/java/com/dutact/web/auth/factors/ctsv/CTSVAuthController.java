package com.dutact.web.auth.factors.ctsv;

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
public class CTSVAuthController {
    private final CTSVAuthService CTSVAuthService;

    public CTSVAuthController(CTSVAuthService CTSVAuthService) {
        this.CTSVAuthService = CTSVAuthService;
    }

    @PostMapping("/api/ctsv/login")
    public ResponseEntity<Object> postLogin(@RequestBody @Valid LoginDto loginDto) {
        try {
            ResponseToken responseToken = CTSVAuthService.login(loginDto);
            return ResponseEntity.ok(responseToken);
        } catch (InvalidLoginCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }
}
