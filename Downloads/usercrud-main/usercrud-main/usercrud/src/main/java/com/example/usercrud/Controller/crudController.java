package com.example.usercrud.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.usercrud.Model.crudModel;
import com.example.usercrud.Service.crudService;

@RestController
@RequestMapping("/api/users")
public class crudController {

    @Autowired
    private crudService service;
    
    @PostMapping
    public crudModel createUser(@RequestBody crudModel user) {
        return service.createUser(user);
    }

    @GetMapping
    public List<crudModel> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public crudModel getUserById(@PathVariable("id") String id) {
        return service.getUserById(id);
    }

    @PutMapping("/{id}")
    public crudModel updateUser(@PathVariable("id") String id, @RequestBody crudModel user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        service.deleteUser(id);
        return "User deleted successfully";
    }
}