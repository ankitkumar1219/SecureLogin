package com.signup.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/signup")
    public String signupPage() {
        return "signup"; // will resolve to templates/signup.html
    }

    @GetMapping("/verify")
    public String verifyPage() {
        return "verify"; // will resolve to templates/verify.html
    }
    
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This maps to login.html (if using templates or static resources)
    }
    
    
    @GetMapping("/forgot")
    public String forgotPage() {
        return "forgot"; // maps to forgot.html
    }
    
    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard"; // This resolves to templates/dashboard.html
    }


}
