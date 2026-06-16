package com.example.usercrud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usercrud.Service.EmailService;
import com.example.usercrud.dto.SumRequest;

@RestController
@RequestMapping("/api/sum")
public class SumController {
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/add")
	public String calculate(@RequestBody SumRequest request) {

	    System.out.println("Controller Hit");

	    int sum = request.getA() + request.getB();

	    emailService.sendSumEmail(
	            request.getEmail(),
	            request.getA(),
	            request.getB());

	    return "Email Sent. Sum = " + sum;
	}
}
