package com.example.demo.domain.repo.impl;

import com.example.demo.auth.AuthService;
import com.example.demo.config.JwtService;
import com.example.demo.domain.service.UserService;
import com.example.demo.model.AuthRequest;
import com.example.demo.model.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse registerUser(UserDto dto) {

        User user = userService.addUser(dto);

        //getting a token
        return getToken(user);
    }

    private AuthResponse getToken(User user) {
        var token = jwtService.generateToken(new HashMap<>(), user);

        return new AuthResponse(token, user.toDto());
    }


    @Override
    public AuthResponse loginUser(AuthRequest authRequest) {
        log.info("resolving {} ", authRequest.email());
        //token generated
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        //user is authenticated
        var user = userService.getByEmail(authRequest.email());
        return getToken(user);
    }


}
