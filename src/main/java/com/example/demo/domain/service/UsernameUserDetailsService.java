package com.example.demo.domain.service;

import com.example.demo.domain.repo.UserRepository;
import com.example.demo.model.User;
import com.example.demo.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UsernameUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UsernameUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("invoked ");
        System.out.println("all "+userRepository.findAll());
        System.out.println("checking "+username);
        User user = userRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name : " + username));
        System.out.println("found "+user);

        System.out.println(username + "," + user);

        return new UserPrincipal(user);
    }


}
