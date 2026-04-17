package com.example.usercrud.Controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.usercrud.Model.ProductModel;
import com.example.usercrud.Service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductModel createProduct(@RequestBody ProductModel product) {
        return service.saveProduct(product);
    }

    @GetMapping
    public List<ProductModel> getProducts() {
        return service.getAllProducts();
    }
}