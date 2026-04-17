package com.example.usercrud.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.usercrud.Model.ProductModel;

public interface ProductRepository extends MongoRepository<ProductModel, String> {
}