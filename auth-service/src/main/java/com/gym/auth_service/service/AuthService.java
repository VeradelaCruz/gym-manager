package com.gym.auth_service.service;

import com.gym.auth_service.models.User;
import com.gym.auth_service.repository.UserRepository;
import com.gym.auth_service.security.JwtService;
import com.gym.auth_service.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//Registrar usuario
//Validar password
//Generar JWT
//Autenticar login
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // 🔹 Registro de usuario
    public void register(String username, String password, Role role, String memberId){

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .memberId(memberId)
                .build();

        userRepository.save(user);
    }

    // 🔹 Login
    public String authenticate(String username, String password){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(user);
    }
}