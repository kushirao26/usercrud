package com.example.usercrud.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderMessage {

    private String username;
    private String email;

    public OrderMessage() {
    }

    public OrderMessage(String username, String email) {
        this.username = username;
        this.email = email;
    }
}