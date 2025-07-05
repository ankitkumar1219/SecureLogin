package com.signup.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String to, String name, String otp) {
        String subject = "🔐 Your OTP Code for SecureApp";
        String message = """
            Hello %s,

            Your One-Time Password (OTP) is: %s

            ⚠️ This OTP is valid for the next 5 minutes only.
            Please do not share it with anyone.

            Regards,  
            Team SecureApp
            """.formatted(name, otp);

        sendSimpleMessage(to, subject, message);
    }

    void sendSimpleMessage(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
