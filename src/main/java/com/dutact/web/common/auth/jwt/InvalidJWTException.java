package com.dutact.web.common.auth.jwt;

public class InvalidJWTException extends RuntimeException {
    public InvalidJWTException(String message) {
        super(message);
    }
}
