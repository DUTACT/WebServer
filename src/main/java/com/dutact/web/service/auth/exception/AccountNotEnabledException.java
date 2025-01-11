package com.dutact.web.service.auth.exception;

public class AccountNotEnabledException extends Exception {
    public AccountNotEnabledException() {
        super("Account is not enabled");
    }
}
