package com.gym.auth_service.models;

import com.gym.auth_service.security.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


//Representa la información del usuario en MongoDB
//Puede ser un profesor, un usuario o el administrador de la pagina
//Guardar datos de autenticación

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String username;

    private String password;

    private String memberId;

    private Role role;

}