package com.dutact.web.auth.factors.admin;

import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AdminAuthController {
    private final AdminAuthService adminAuthService;

    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }
    @Operation(summary = "Login for admin users", description = "Authenticate an admin user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, token returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseToken.class),
                            examples = @ExampleObject(value = "{\n  \"accessToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MjY3NDY2NzUsImV4cCI6MTcyNzYxMDY3NSwic3ViIjoiMTAyMjEwMTM0QHN2MS5kdXQudWRuLnZuIiwic2NwIjpbIlJPTEVfU1RVREVOVCJdfQ.EBaf6BI3L3-dpSfLYRb-W3939MuJESoomI66L-zyiRQ\"\n}")
                    )),
            @ApiResponse(responseCode = "401", description = "Invalid login credentials",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"message\": \"Invalid login credentials\"\n}")
                    ))
    })
    @PostMapping("/api/admin/login")
    public ResponseEntity<Object> postLogin(@RequestBody @Valid LoginDto loginDto) {
        try {
            ResponseToken responseToken = adminAuthService.login(loginDto);
            return ResponseEntity.ok(responseToken);
        }
        catch (InvalidLoginCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }
}
