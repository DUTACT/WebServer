package com.dutact.web.auth.factors.student;

import com.dutact.web.auth.dto.ConfirmDto;
import com.dutact.web.auth.dto.student.StudentConfirmResetPasswordDto;
import com.dutact.web.auth.dto.student.StudentRegisterDto;
import com.dutact.web.auth.dto.student.StudentResetPasswordDto;
import com.dutact.web.auth.exception.OtpException;
import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.auth.exception.UsernameOrEmailNotExistException;
import com.dutact.web.common.api.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentAuthController {
    private final StudentAuthService studentAuthService;

    public StudentAuthController(StudentAuthService studentAuthService) {
        this.studentAuthService = studentAuthService;
    }

    @Operation(summary = "Register a new student", description = "Register a new student with username, email, and other required fields. A OTP will be send to email for next confirmation step")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student successfully registered",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Username or email already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error sending email",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })

    @PostMapping("/api/student/register")
    public ResponseEntity<Object> postRegister(@RequestBody @Valid StudentRegisterDto registerDto) {
        try {
            studentAuthService.register(registerDto);
        } catch (UsernameOrEmailAlreadyExistException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (MessagingException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/api/student/confirm-registration")
    @Operation(summary = "Confirm student registration", description = "Confirm the student's registration using an OTP, successful will enable the student account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registration confirmed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid OTP or confirmation details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<Object> confirmRegistration(@RequestBody @Valid ConfirmDto confirmDto) {
        try {
            studentAuthService.confirmRegistration(confirmDto);
        }
        catch (OtpException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/student/reset-password")
    @Operation(summary = "Reset student password", description = "Request to reset a student's password. An email with the otp will be sent.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Password reset request accepted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Username or email does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error sending email",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid StudentResetPasswordDto resetPasswordDto) {
        try {
            studentAuthService.resetPassword(resetPasswordDto);
        }
        catch (UsernameOrEmailNotExistException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (MessagingException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/api/student/reset-password")
    @Operation(summary = "Confirm password reset", description = "Confirm and set a new password for the student using an OTP.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password reset confirmed and updated successfully",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid or expired OTP",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
    })
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid StudentConfirmResetPasswordDto confirmResetPasswordDto) {
        try {
            studentAuthService.resetNewPassword(confirmResetPasswordDto);
        }
        catch (OtpException e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.noContent().build();
    }
}