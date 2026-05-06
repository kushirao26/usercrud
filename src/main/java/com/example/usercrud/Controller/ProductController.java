package com.example.usercrud.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PreAuthorize("hasRole('ADMIN')")
    public ProductModel createProduct(@RequestBody ProductModel product) {
        return service.saveProduct(product);
    }

    @GetMapping
    public List<ProductModel> getProducts() {
        return service.getAllProducts();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductModel updateProduct(@PathVariable("id") String id,
                                      @RequestBody ProductModel product) {
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct(@PathVariable("id") String id) {
        service.deleteProduct(id);
        return "Product deleted successfully";
    }

    @PostMapping("/upload")
    public String uploadCSV(@RequestParam("file") MultipartFile file,
                            @RequestParam("delimiter") String delimiter) {
        return service.saveCSVData(file, delimiter);
    }
}