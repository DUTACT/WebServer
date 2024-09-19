package com.dutact.web.auth.exception;

public class TokenWithInvalidInfoException extends RuntimeException{
    public TokenWithInvalidInfoException() {
        super("Token with invalid info");
    }
}
