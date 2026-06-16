package com.example.usercrud.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.usercrud.Model.crudModel;
import com.example.usercrud.Service.crudService;

@RestController
@RequestMapping("/api/users")
public class crudController {

    private final crudService service;

    public crudController(crudService service) {
        this.service = service;
    }

    @PostMapping
    public crudModel create(@RequestBody crudModel user) {
        return service.createUser(user);
    }

    @GetMapping("/{id}")
    public crudModel getById(@PathVariable("id") Long id) {
        return service.getUserById(id);
    }

    @GetMapping
    public List<crudModel> getAll() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public crudModel update(@PathVariable("id") Long id,
                            @RequestBody crudModel user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteUser(id);
    }
}