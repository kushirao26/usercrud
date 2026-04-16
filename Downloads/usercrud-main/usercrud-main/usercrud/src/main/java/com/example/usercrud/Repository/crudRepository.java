package com.example.usercrud.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.usercrud.Model.crudModel;

public interface crudRepository extends MongoRepository<crudModel, String> {
}