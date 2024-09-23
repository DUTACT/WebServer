package com.dutact.web.auth.otp;

import com.dutact.web.common.email.EmailSenderService;
import com.dutact.web.common.email.EmailTemplateService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class OtpService {
    private final Long EXPIRE_MS;
    private final OtpGenerator otpGenerator;
    private final EmailSenderService emailService;
    private final EmailTemplateService emailTemplateService;

    public OtpService(@Value("${auth.otp.lifespan}") Long otpLifespan, OtpGenerator otpGenerator, EmailSenderService emailService, EmailTemplateService emailTemplateService) {
        this.EXPIRE_MS = otpLifespan;
        this.otpGenerator = otpGenerator;
        this.emailService = emailService;
        this.emailTemplateService = emailTemplateService;
    }

    public void sendOtp(String email) throws MessagingException {
        Integer otp = otpGenerator.generateOTP(email);
        emailService.sendEmail(email, "Mã OTP của bạn", emailTemplateService.generateEmailContent("otp-verification", Map.of("otp", otp, "expiredMin", EXPIRE_MS / 60000)));
    }

    public boolean verifyOtp(String email, Integer otp) {
        return otpGenerator.getOPTByKey(email).equals(otp);
    }
}
