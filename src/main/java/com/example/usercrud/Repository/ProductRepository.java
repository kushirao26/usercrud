package com.example.usercrud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usercrud.Model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, String> {
}