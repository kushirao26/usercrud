package com.example.usercrud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usercrud.Model.crudModel;

public interface crudRepository extends JpaRepository<crudModel, Long> {
    crudModel findByUsername(String username);
}