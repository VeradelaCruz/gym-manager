package com.gym.gym_api_gateway.security;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    public boolean validateToken(String token){

        try{
            String SECRET = "gym-secret-key";
            Jwts.parserBuilder()
                    .setSigningKey(SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token);

            return true;

        }catch(Exception e){
            return false;
        }
    }
}