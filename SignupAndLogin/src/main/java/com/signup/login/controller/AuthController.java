package com.signup.login.controller;




import com.signup.login.entity.User;
import com.signup.login.repository.UserRepository;
import com.signup.login.service.EmailService;
import com.signup.login.service.OtpService;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private EmailService emailService;
    @Autowired private OtpService otpService;

    // 📩 SIGNUP - Send OTP
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("❌ Email already registered.");
        }

        user.setVerified(false);
        userRepo.save(user);

        String otp = otpService.generateOtp();
        otpService.saveOtp(user.getEmail(), otp);
        emailService.sendOtpEmail(user.getEmail(), user.getName(), otp);


        return ResponseEntity.ok("✅ OTP sent to email. Please verify.");
    }

    // ✅ VERIFY OTP
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        if (otpService.validateOtp(email, otp)) {
            User user = userRepo.findByEmail(email);
            user.setVerified(true);
            userRepo.save(user);
            return ResponseEntity.ok("🎉 Account verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("❌ Invalid or expired OTP.");
        }
    }
    
    @PostMapping("/send-reset-otp")
    public ResponseEntity<String> sendResetOtp(@RequestParam String email) {
        return otpService.sendOtp(email); // same service used before
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        Optional<User> userOpt = Optional.of(userRepo.findByEmail(email));
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(newPassword); // in real app hash this
            userRepo.save(user);
            return ResponseEntity.ok("Password reset successful");
        }

        return ResponseEntity.badRequest().body("User not found");
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        User user = userRepo.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Email not registered.");
        }

        if (!user.isVerified()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("⚠️ Account not verified.");
        }

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Incorrect password.");
        }

        return ResponseEntity.ok("Login successful");
    }

    

}

