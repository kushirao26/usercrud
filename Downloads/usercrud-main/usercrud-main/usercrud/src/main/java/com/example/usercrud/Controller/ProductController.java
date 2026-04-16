package com.example.usercrud.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.usercrud.Model.ProductModel;
import com.example.usercrud.Service.ProductService;
import com.example.usercrud.security.JwtUtil;   // ✅ IMPORT

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    private final JwtUtil jwtUtil; 

    public ProductController(ProductService service, JwtUtil jwtUtil) { 
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ProductModel createProduct(@RequestBody ProductModel product) {
        return service.saveProduct(product);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Token Format");
        }

        String actualToken = token.substring(7);

        if (jwtUtil.validateToken(actualToken)) {
            return ResponseEntity.ok(service.getAllProducts());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or Expired Token");
        }
    }
}