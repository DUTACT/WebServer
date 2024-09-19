package com.dutact.web.auth.exception;

public class InvalidLoginCredentialsException extends RuntimeException{
    public InvalidLoginCredentialsException() {
        super("Invalid login credentials");
    }
}
