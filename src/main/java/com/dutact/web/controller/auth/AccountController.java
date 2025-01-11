package com.dutact.web.controller.auth;

import com.dutact.web.dto.auth.LoginDto;
import com.dutact.web.dto.auth.ResponseToken;
import com.dutact.web.service.auth.AccountService;
import com.dutact.web.service.auth.exception.AccountNotEnabledException;
import com.dutact.web.service.auth.exception.InvalidLoginCredentialsException;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/login")
    @Operation(
            summary = "user Login",
            description = "Authenticates the user and returns a JWT token upon successful login."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = ResponseToken.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials or account not enabled",
                    content = @Content(schema = @Schema(implementation = LoginFailedResponse.class)))
    })
    public ResponseEntity<Object> postLogin(@RequestBody @Valid LoginDto loginDto) {
        try {
            ResponseToken responseToken = accountService.login(loginDto);
            return ResponseEntity.ok(responseToken);
        } catch (InvalidLoginCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginFailedResponse(LoginFailedStatus.INVALID_LOGIN_CREDENTIALS));
        } catch (AccountNotEnabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginFailedResponse(LoginFailedStatus.ACCOUNT_NOT_ENABLED));
        }
    }
}

class LoginFailedStatus {
    public static final String INVALID_LOGIN_CREDENTIALS = "invalid_credentials";
    public static final String ACCOUNT_NOT_ENABLED = "account_not_enabled";
}

@Data
@AllArgsConstructor
class LoginFailedResponse {
    @Schema(allowableValues = {
            LoginFailedStatus.INVALID_LOGIN_CREDENTIALS,
            LoginFailedStatus.ACCOUNT_NOT_ENABLED})
    @JsonProperty("status")
    private String status;
}
