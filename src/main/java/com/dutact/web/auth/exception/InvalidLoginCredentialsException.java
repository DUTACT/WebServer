package com.dutact.web.auth.exception;

public class InvalidLoginCredentialsException extends Exception{
    public InvalidLoginCredentialsException() {
        super("Invalid login credentials");
    }
}
