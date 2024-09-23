package com.dutact.web.common.email;

public class SendEmailException extends Exception {
    public SendEmailException() {
        super("Failed to send email");
    }
}
