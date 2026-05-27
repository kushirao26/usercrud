package com.example.usercrud.Controller;

import com.example.usercrud.Service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/send")
    public String send(@RequestParam String message) {

        System.out.println("API HIT");

        kafkaProducerService.sendMessage(message);

        return "Message sent";
    }
    
}