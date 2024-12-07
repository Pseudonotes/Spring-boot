package com.example.demo.model;

public class AuthResponse {
    private String token;
    private UserDto userDto;

    public AuthResponse() {
    }

    public AuthResponse(String token, UserDto userDto) {
        this.token = token;
        this.userDto = userDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", userDto=" + userDto +
                '}';
    }
}
