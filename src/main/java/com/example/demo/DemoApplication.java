package com.example.demo;

import com.example.demo.domain.repo.UserRepository;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@ControllerAdvice
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create")
    public User createUser(@RequestBody UserDto user) {
//        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//        return userRepository.save(user);
        return new User();
    }


    @PostMapping("/signin")
    public String loginUser(@RequestBody UserDto user) {
        return "login failed";
    }


    @GetMapping("/hello")
    public String helloController(HttpServletRequest request) {
        return "Hello world." + request.getSession().getId();
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

}
