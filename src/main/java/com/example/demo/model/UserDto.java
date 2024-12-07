package com.example.demo.model;

public record UserDto(
        String firstName,
        String lastName,
        String email,
        String password
        ) {

    public User fromDto(){
        return new User(firstName,lastName,email,password);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
