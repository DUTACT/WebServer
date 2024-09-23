package com.dutact.web.auth.exception;

public class UsernameOrEmailAlreadyExistException extends Exception{
    public UsernameOrEmailAlreadyExistException() {
        super("Username or email already exist");
    }
}
