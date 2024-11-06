package com.dutact.web.auth.exception;

public class InvalidCredentialsException extends Exception{
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
