package com.dutact.web.service.auth.exception;

public class InvalidLoginCredentialsException extends Exception {
    public InvalidLoginCredentialsException() {
        super("Invalid login credentials");
    }
}
