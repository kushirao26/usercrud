package com.example.usercrud.Service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usercrud.Model.crudModel;
import com.example.usercrud.Repository.crudRepository;

@Service
public class crudService {

    private final crudRepository repository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

    @Cacheable(value = "users", key = "#p0")
    public crudModel getUserById(Long id) {
        System.out.println("========== DB HIT ==========");
        return repository.findById(id).orElse(null);
    }

    @CachePut(value = "users", key = "#p0")
    public crudModel updateUser(Long id, crudModel newUser) {

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
    
    @CacheEvict(value = "users", key = "#p0")
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public crudModel getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Async("taskExecutor")
    public void sendBackgroundLog(Long userId) {
        System.out.println("Async running thread: " + Thread.currentThread().getName()
                + " | userId: " + userId);
    }
}