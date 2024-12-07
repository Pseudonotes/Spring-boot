package com.example.demo.model;

public record AuthRequest(
        String email,
        String password
) {
}
