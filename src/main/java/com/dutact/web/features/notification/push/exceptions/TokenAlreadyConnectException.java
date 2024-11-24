package com.dutact.web.features.notification.push.exceptions;

public class TokenAlreadyConnectException extends Exception {
    public TokenAlreadyConnectException() {
        super("Token already connected");
    }
}
