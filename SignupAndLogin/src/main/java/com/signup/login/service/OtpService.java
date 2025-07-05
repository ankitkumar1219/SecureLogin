



package com.signup.login.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.signup.login.entity.Otp;
import com.signup.login.entity.User; // ✅ THIS IS THE MISSING ONE
import com.signup.login.repository.OtpRepository;
import com.signup.login.repository.UserRepository;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<String> sendOtp(String email) {
        Optional<User> userOpt = Optional.of(userRepository.findByEmail(email));

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String generatedOtp = String.valueOf((int) ((Math.random() * 9000) + 1000));

        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setOtp(generatedOtp);
        otp.setCreatedAt(LocalDateTime.now());

        otpRepository.save(otp);
        String name = userOpt.get().getName();
        emailService.sendOtpEmail(email, name, generatedOtp);


        return ResponseEntity.ok("OTP sent to your email");
    }

    public boolean validateOtp(String email, String inputOtp) {
        Otp latestOtp = otpRepository.findTopByEmailOrderByCreatedAtDesc(email);
        return latestOtp != null && latestOtp.getOtp().equals(inputOtp);
    }
    public String generateOtp() {
        return String.valueOf((int) ((Math.random() * 9000) + 1000));
    }
    
    
    public void saveOtp(String email, String otp) {
        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setCreatedAt(java.time.LocalDateTime.now());
        otpRepository.save(otpEntity);
    }

}
