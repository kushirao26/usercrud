package com.example.usercrud.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
@Document(collection = "users")
@Getter
@Setter
public class crudModel {

    @Id
    private String id;
    
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String password;

   }