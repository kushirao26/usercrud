package com.example.usercrud.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Application Running";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication) {
        return authentication.getName();
    }
}