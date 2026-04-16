package com.example.usercrud.Controller;

import org.springframework.web.bind.annotation.*;
import com.example.usercrud.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final JwtUtil jwtUtil;

    public LoginController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username) {
        return jwtUtil.generateToken(username);
    }
}