package com.example.demo;

import com.example.demo.domain.repo.UserRepository;
import com.example.demo.model.User;
import com.example.demo.model.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Bean
	public CommandLineRunner commandLineRunner(){

		return (args)->{
			System.out.println("args "+ Arrays.toString(args));
		};
	}

	@PostMapping("/create")
	public User createUser(@RequestBody UserDto user){
		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		return userRepository.save(user.toUser(encoder.encode(user.password())));
	}


	@GetMapping("/hello")
	public String helloController(HttpServletRequest request){
		return "Hello world." + request.getSession().getId();
	}

	@GetMapping("/csrf")
	public CsrfToken getCsrfToken(HttpServletRequest request){
		return (CsrfToken) request.getAttribute("_csrf");
	}

}
