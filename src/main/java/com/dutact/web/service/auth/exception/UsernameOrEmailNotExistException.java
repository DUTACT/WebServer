package com.dutact.web.service.auth.exception;

public class UsernameOrEmailNotExistException extends Exception {
    public UsernameOrEmailNotExistException() {
        super("Username or email not exist");
    }
}
