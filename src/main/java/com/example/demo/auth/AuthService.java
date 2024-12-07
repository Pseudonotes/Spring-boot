package com.example.demo.auth;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;

public interface AuthService {

    AuthResponse registerUser(UserDto dto);

    AuthResponse loginUser(AuthRequest dto);

}
