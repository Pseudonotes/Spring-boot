package com.example.demo.domain.service;

import com.example.demo.model.User;
import com.example.demo.model.UserDto;

public interface UserService {
    User addUser(UserDto dto);
    User getByEmail(String email);
    void removeUser(UserDto dto);
    User updateUser(UserDto dto);
}
