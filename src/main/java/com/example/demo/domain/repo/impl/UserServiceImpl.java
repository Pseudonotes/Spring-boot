package com.example.demo.domain.repo.impl;

import com.example.demo.domain.repo.UserRepository;
import com.example.demo.domain.service.UserService;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User addUser(UserDto dto) {
        User user = dto.fromDto();
        user.setPassword(passwordEncoder.encode(dto.password()));//encoding password
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Missing user : " + email));
    }

    @Override
    public void removeUser(UserDto dto) {
        userRepository.delete(
                userRepository.findByEmail(dto.email())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found."))
        );
    }

    @Override
    public User updateUser(UserDto dto) {
        userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        return userRepository.save(dto.fromDto());
    }

}
