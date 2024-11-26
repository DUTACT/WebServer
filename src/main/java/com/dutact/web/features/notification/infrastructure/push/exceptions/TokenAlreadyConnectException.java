package com.dutact.web.features.notification.infrastructure.push.exceptions;

public class TokenAlreadyConnectException extends Exception {
    public TokenAlreadyConnectException() {
        super("Token already connected");
    }
}
