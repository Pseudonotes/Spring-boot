package com.example.demo.model;

public record UserDto(
        String name,
        String password
) {

    public User toUser(String encrypted){
        return new User(name,encrypted);
    }

}
