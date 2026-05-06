package com.example.usercrud.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.usercrud.Model.crudModel;
import com.example.usercrud.Repository.crudRepository;

@Service
public class crudService {

    private final crudRepository repository;
    private final EmailService emailService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public crudService(crudRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public crudModel createUser(crudModel user) {
        user.setPassword(encoder.encode(user.getPassword()));

        crudModel savedUser = repository.save(user);

        emailService.sendEmailWithPdf(
                savedUser.getEmail(),
                savedUser.getUsername()
        );

        return savedUser;
    }

    public List<crudModel> getAllUsers() {
        return repository.findAll();
    }

    public crudModel getUserById(String id) {
        return repository.findById(id).orElse(null);
    }

    public crudModel updateUser(String id, crudModel newUser) {
        crudModel user = repository.findById(id).orElse(null);

        if (user != null) {
            user.setUsername(newUser.getUsername());
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setEmail(newUser.getEmail());
            user.setContactNumber(newUser.getContactNumber());

            if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
                user.setPassword(encoder.encode(newUser.getPassword()));
            }

            return repository.save(user);
        }
        return null;
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    public crudModel getByUsername(String username) {
        return repository.findByUsername(username);
    }
}