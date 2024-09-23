package com.dutact.web.auth.exception;

public class OtpException extends Exception{
    public OtpException() {
        super("Invalid OTP");
    }
}
