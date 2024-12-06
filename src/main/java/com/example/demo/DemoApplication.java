package com.example.demo;

import com.example.demo.domain.repo.UserRepository;
import com.example.demo.domain.service.JwtService;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/create")
    public User createUser(@RequestBody UserDto user) {
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        return userRepository.save(user.toUser(encoder.encode(user.password())));
    }


    @PostMapping("/signin")
    public String loginUser(@RequestBody UserDto user) {
        if (
                //details forwarded to auth manager for confirmation
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(user.name(), user.password()))
                        .isAuthenticated()
        )
            return jwtService.generateToken(user.name());
        else
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
