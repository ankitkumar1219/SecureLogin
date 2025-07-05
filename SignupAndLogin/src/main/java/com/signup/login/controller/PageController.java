package com.signup.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/signup")
    public String signupPage() {
        return "signup"; 
    }

    @GetMapping("/verify")
    public String verifyPage() {
        return "verify"; 
    }
    
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }
    
    
    @GetMapping("/forgot")
    public String forgotPage() {
        return "forgot"; 
    }
    
    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard"; 
    }


}
