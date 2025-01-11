package com.dutact.web.service.auth.exception;

public class OtpException extends Exception {
    public OtpException() {
        super("Invalid OTP");
    }
}
