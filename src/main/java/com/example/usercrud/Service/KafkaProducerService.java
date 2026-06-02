package com.example.usercrud.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.usercrud.Model.crudModel;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String message) {

        kafkaTemplate.send("user-topic", message);

        System.out.println("Message Sent: " + message);
    }

    public void sendUser(crudModel user) {

        kafkaTemplate.send("user-topic", user);

        System.out.println("USER OBJECT SENT");
    }
}