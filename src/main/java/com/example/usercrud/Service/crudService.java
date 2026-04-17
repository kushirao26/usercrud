package com.example.usercrud.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.usercrud.Model.crudModel;
import com.example.usercrud.Repository.crudRepository;

@Service
public class crudService {

	@Autowired
    private crudRepository repository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public crudModel createUser(crudModel user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
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
