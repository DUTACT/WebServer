package com.dutact.web.auth.factors.student;

import com.dutact.web.auth.dto.ConfirmTokenDto;
import com.dutact.web.auth.dto.LoginDto;
import com.dutact.web.auth.dto.ResponseToken;
import com.dutact.web.auth.dto.student.StudentConfirmResetPasswordDto;
import com.dutact.web.auth.dto.student.StudentRegisterDto;
import com.dutact.web.auth.dto.student.StudentResetPasswordDto;
import com.dutact.web.auth.exception.InvalidLoginCredentialsException;
import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.auth.exception.UsernameOrEmailNotExistException;
import com.dutact.web.auth.token.jwt.InvalidJWTException;
import com.dutact.web.core.repositories.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class StudentAuthController {
    private final StudentAuthService studentAuthService;

    public StudentAuthController(StudentAuthService studentAuthService) {
        this.studentAuthService = studentAuthService;
    }
    @Operation(summary = "Login for students", description = "Authenticate a student and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, token returned",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"accessToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MjY3NDY2NzUsImV4cCI6MTcyNzYxMDY3NSwic3ViIjoiMTAyMjEwMTM0QHN2MS5kdXQudWRuLnZuIiwic2NwIjpbIlJPTEVfU1RVREVOVCJdfQ.EBaf6BI3L3-dpSfLYRb-W3939MuJESoomI66L-zyiRQ\"\n}")
                    )),
            @ApiResponse(responseCode = "401", description = "Invalid login credentials",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"message\": \"Invalid login credentials\"\n}")
                    ))
    })
    @PostMapping("/api/student/login")
    public ResponseEntity<Object> postLogin(@RequestBody @Valid LoginDto loginDto) {
        try {
            ResponseToken responseToken = studentAuthService.login(loginDto);
            return ResponseEntity.ok(responseToken);
        }
        catch (InvalidLoginCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }

    @Operation(summary = "Register a new student", description = "Send a email verification link to the student's email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registration successful",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Username or email already exists",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"message\": \"Username or email already exists\"\n}")
                    ))
    })
    @PostMapping("/api/student/register")
    public ResponseEntity<Object> postRegister(@RequestBody @Valid StudentRegisterDto registerDto) {
        try {
            studentAuthService.register(registerDto);
        } catch (UsernameOrEmailAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Confirm student registration", description = "Confirms a student’s email using a verification token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registration confirmed, no content"),
            @ApiResponse(responseCode = "400", description = "Invalid verification token",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"message\": \"Invalid JWT token\"\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "Username or email already exists",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"message\": \"Username or email already exists\"\n}")
                    ))
    })
    @GetMapping("/api/student/confirm-registration/{token}")
    public ResponseEntity<Object> confirmRegistration(@PathVariable("token") String verificationToken) {
        try {
            studentAuthService.confirmRegistration(new ConfirmTokenDto(verificationToken));
        }
        catch (UsernameOrEmailAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        }
        catch (InvalidJWTException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Request a password reset", description = "Send a link to reset the student's password to their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Password reset email sent",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Username or email not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"message\": \"Username or email does not exist\"\n}")
                    ))
    })
    @PostMapping("/api/student/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid StudentResetPasswordDto resetPasswordDto) {
        try {
            studentAuthService.resetPassword(resetPasswordDto);
        }
        catch (UsernameOrEmailNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
        studentAuthService.resetPassword(resetPasswordDto);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Confirm password reset", description = "Resets the student’s password using the provided token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password reset successfully, no content"),
            @ApiResponse(responseCode = "400", description = "Invalid reset token",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"message\": \"Invalid JWT token\"\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Username or email not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"message\": \"Username or email does not exist\"\n}")
                    ))
    })
    @PutMapping("/api/student/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid StudentConfirmResetPasswordDto confirmResetPasswordDto) {
        try {
            studentAuthService.resetNewPassword(confirmResetPasswordDto);
        }
        catch (UsernameOrEmailNotExistException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
        catch (InvalidJWTException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.noContent().build();
    }
}
