package com.dutact.web.auth.exception;

public class UsernameOrEmailAlreadyExistException extends RuntimeException{
    public UsernameOrEmailAlreadyExistException() {
        super("Username or email already exist");
    }
}
