package com.gym.auth_service.security;

import com.gym.auth_service.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

//Crear y validar tokens
// Maneja JWT directamente.
//Generar token
//Extraer username
//Extraer role
//Validar firma
//Validar expiración
@Component
public class JwtService {

    public String generateToken(User user){

        String SECRET = "gym-secret-key";
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .claim("memberId", user.getMemberId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}