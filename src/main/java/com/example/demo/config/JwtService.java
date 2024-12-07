package com.example.demo.config;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    final String SECRET_KEY = "b2eeb4f16c8e4a9f9f3c1a45b5b37410f89d85cfcf9a1f489dab26d1d5a7c693";


    String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    );

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> resolver);

    Claims extractAllClaims(String token);

    SecretKey getSigningKey();

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);
}
