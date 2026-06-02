package com.example.usercrud.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.usercrud.Model.crudModel;

@Service
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @KafkaListener(
        topics = "user-topic",
        groupId = "email-group"
    )
    public void consume(crudModel user) {

        System.out.println("EMAIL SUBSCRIBER");

        emailService.sendEmailWithPdf(
                user.getEmail(),
                user.getUsername()
        );
    }
}