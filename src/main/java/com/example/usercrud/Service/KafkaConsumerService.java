package com.example.usercrud.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.usercrud.Model.crudModel;

@Service
public class KafkaConsumerService {

	@KafkaListener(topics = "user-topic", groupId = "group-id")
	public void consume(crudModel user) {

	    System.out.println("USER RECEIVED");

	    System.out.println(user.getUsername());

	    System.out.println(user.getEmail());
	}
}