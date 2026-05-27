package com.example.usercrud.Controller;

import com.example.usercrud.Service.JsonAnalyzerService;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/json")
public class JsonAnalyzerController {

    private final JsonAnalyzerService service;

    public JsonAnalyzerController(JsonAnalyzerService service) {
        this.service = service;
    }

    @PostMapping("/analyze")
    public Object analyzeJson(@RequestBody JsonNode json) {

        System.out.println("JSON ANALYZE HIT");

        return service.analyzeJson(json);
    }
}