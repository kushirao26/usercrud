package com.example.usercrud.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.usercrud.Model.SchemeNode;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class JsonAnalyzerService {
    public Object analyzeJson(JsonNode root) {
        try {
            if (root.isObject()) {
                Map<String, Object> result = new LinkedHashMap<>();
                root.fields().forEachRemaining(entry -> {result.put(entry.getKey(),analyzeNode(entry.getValue()));
                });
                return result;
            }
            else if (root.isArray()) {
                List<Object> result = new ArrayList<>();
                if (root.size() > 0) {
                    result.add(
                            analyzeNode(root.get(0)));
                }
                return result;
            }
            return analyzeNode(root);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON: " + e.getMessage());
        }
    }
    private SchemeNode analyzeNode(JsonNode node) {
        SchemeNode schema = new SchemeNode();
        if (node.isObject()) {
            schema.setDataType("Object");
            Map<String, Object> childMap = new LinkedHashMap<>();
            node.fields().forEachRemaining(entry -> {
                childMap.put(
                        entry.getKey(),
                        analyzeNode(entry.getValue()));
            });
            schema.setChildren(childMap);
        }
        else if (node.isArray()) {
            schema.setDataType("Array");
            List<Object> children = new ArrayList<>();
            if (node.size() > 0) {
                children.add(analyzeNode(node.get(0)));
            }
            schema.setChildren(children);
        }
        else if (node.isTextual()) {
            schema.setDataType("String");
        }
        else if (node.isInt() || node.isLong()) {
            schema.setDataType("Integer");
        }
        else if (node.isFloat() || node.isDouble() || node.isBigDecimal()) {
            schema.setDataType("Float");
        }
        else if (node.isBoolean()) {
            schema.setDataType("Boolean");
        }
        else if (node.isNull()) {
            schema.setDataType("Null");
        }
        else {
            schema.setDataType("Unknown");
        }
        return schema;
    }
}