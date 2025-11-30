package com.gym.notification_service.service;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;


//Propósito:
//Genera el contenido HTML del email reemplazando valores dinámicos como {{name}} o {{amount}}.
//Separa la lógica de contenido de la lógica de envío.
@Component
public class EmailTemplateProcessor {

    public String loadTemplate(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/resources/templates/" + path)));
        } catch (Exception e) {
            throw new RuntimeException("Error cargando plantilla", e);
        }
    }

    public String replace(String template, String placeholder, String value) {
        return template.replace("{{" + placeholder + "}}", value);
    }
}