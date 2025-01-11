package com.dutact.web.service.auth.exception;

public class UsernameOrEmailAlreadyExistException extends Exception {
    public UsernameOrEmailAlreadyExistException() {
        super("Username or email already exist");
    }
}
