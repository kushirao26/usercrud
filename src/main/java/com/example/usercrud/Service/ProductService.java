package com.example.usercrud.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usercrud.Model.ProductModel;
import com.example.usercrud.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public ProductModel saveProduct(ProductModel product) {
        return repository.save(product);
    }

    public List<ProductModel> getAllProducts() {
        return repository.findAll();
    }
    public ProductModel updateProduct(String id, ProductModel product) {
        return repository.findById(id).map(existing -> {
            existing.setName(product.getName());
            existing.setPrice(product.getPrice());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }
}
