package com.example.usercrud.Model;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuditConsumer {

    @KafkaListener(
        topics = "user-topic",
        groupId = "audit-group"
    )
    public void consume(crudModel user) {

        System.out.println("AUDIT SUBSCRIBER");

        System.out.println(
            "New User Registered: "
            + user.getUsername()
        );
    }
}
