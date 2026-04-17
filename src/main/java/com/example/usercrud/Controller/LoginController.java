package com.example.usercrud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.usercrud.Model.LoginModel;
import com.example.usercrud.Model.crudModel;
import com.example.usercrud.Service.crudService;
import com.example.usercrud.security.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private crudService service;

    @Autowired
    private JwtUtil jwtUtil; 

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public String login(@RequestBody LoginModel request) {

        crudModel user = service.getByUsername(request.getUsername());

        if (user != null &&
                encoder.matches(request.getPassword(), user.getPassword())) {

            return jwtUtil.generateToken(user.getUsername());
        }

        return "Invalid Username or Password";
    }
}