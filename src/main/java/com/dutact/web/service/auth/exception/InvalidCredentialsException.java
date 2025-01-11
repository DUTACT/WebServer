package com.dutact.web.service.auth.exception;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
