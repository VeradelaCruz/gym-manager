package com.gym.notification_service.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    private String id;

    /** ID del usuario destinatario */
    @NotBlank(message = "El ID del usuario no puede estar vacío")
    private String userId;

    /** Dirección de correo del usuario (opcional si el tipo no es EMAIL) */
    @Email(message = "El formato del email no es válido")
    private String email;

    /** Contenido principal del mensaje */
    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(max = 500, message = "El mensaje no puede exceder los 500 caracteres")
    private String message;

    /** Tipo de notificación (EMAIL, SMS, PUSH) */
    @NotBlank(message = "El tipo de notificación es obligatorio")
    @Pattern(regexp = "EMAIL|SMS|PUSH", message = "El tipo debe ser EMAIL, SMS o PUSH")
    private String type;

    /** Microservicio origen que generó la notificación */
    @NotBlank(message = "El servicio de origen no puede estar vacío")
    private String sourceService;

    /** Indica si la notificación fue enviada con éxito */
    private boolean sent;

    /** Fecha/hora de envío (null si aún no se envió) */
    private LocalDateTime sentAt;

    /** Fecha/hora de creación del registro */
    @PastOrPresent(message = "La fecha de creación no puede estar en el futuro")
    private LocalDateTime createdAt;
}
