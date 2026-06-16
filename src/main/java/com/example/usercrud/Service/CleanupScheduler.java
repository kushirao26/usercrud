package com.example.usercrud.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CleanupScheduler {

    @Scheduled(fixedRate = 60000)
    public void cleanLogs() {
        System.out.println("Running scheduled cleanup: " + System.currentTimeMillis());
    }
}