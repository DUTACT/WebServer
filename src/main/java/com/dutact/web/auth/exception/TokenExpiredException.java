package com.dutact.web.auth.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException() {
        super("Token expired");
    }
}
