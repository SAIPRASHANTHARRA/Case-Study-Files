package com.api_gateway.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

@Service
public class JWTService {

    private final String secret = "your-256-bit-secret-for-jwt-should-be-long"; // Use env/config
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            return null;
        }
    }
}

