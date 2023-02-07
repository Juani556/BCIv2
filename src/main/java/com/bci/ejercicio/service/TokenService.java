package com.bci.ejercicio.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class TokenService {

    @Value("${token.jwt.key}")
    private String secretKey;

    @Value("${token.jwt.expiration}")
    private Long expirationTime;

    public String generateTokenForUser(String mail) {

        Date expiration = new Date(new Date().getTime() + expirationTime);

        return Jwts.builder().setSubject(mail).setExpiration(expiration).signWith(getKey()).compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String getSubject(String token) {

        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getSubject();
    }
}
