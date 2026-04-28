package com.example.usercrud.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSignupEmail(String toEmail, String username) {

        try {
            System.out.println("Trying to send email to: " + toEmail);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Registration Successful");
            message.setText("Hello " + username + ", your account created successfully.");

            mailSender.send(message);

            System.out.println("Email Sent Successfully");

        } catch (Exception e) {
            System.out.println("Mail Failed: " + e.getMessage());
        }
    }
}