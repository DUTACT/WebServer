package com.dutact.web.auth.exception;

public class UsernameOrEmailNotExistException extends RuntimeException{
    public UsernameOrEmailNotExistException() {
        super("Username or email not exist");
    }
}
