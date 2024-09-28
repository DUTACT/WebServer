package com.dutact.web.auth.otp;

import com.dutact.web.auth.exception.OtpException;
import com.dutact.web.common.email.EmailSenderService;
import com.dutact.web.common.email.EmailTemplateService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class OtpService {
    private final Long EXPIRE_MS;
    private final OtpRepository otpRepository;
    private final EmailSenderService emailService;
    private final EmailTemplateService emailTemplateService;

    public OtpService(@Value("${auth.otp.lifespan}") Long otpLifespan, OtpRepository otpRepository, EmailSenderService emailService, EmailTemplateService emailTemplateService) {
        this.EXPIRE_MS = otpLifespan;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
        this.emailTemplateService = emailTemplateService;
    }

    public void sendOtp(String email) throws MessagingException {
        Long otp = otpRepository.generateOTP(email, EXPIRE_MS);
        emailService.sendEmail(email, "Mã OTP của bạn", emailTemplateService.generateEmailContent("otp-verification", Map.of("otp", otp, "expiredMin", EXPIRE_MS / 60000)));
    }

    public boolean verifyOtp(String email, Long otp) throws OtpException {
        return otpRepository.getOPTByKey(email).equals(otp);
    }
}
