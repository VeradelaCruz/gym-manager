package com.gym.class_service.controller;

import com.gym.class_service.dtos.FitnessClassCreateRequest;
import com.gym.class_service.dtos.FitnessClassResponse;
import com.gym.class_service.models.FitnessClass;
import com.gym.class_service.service.ClassService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    ClassService classService;

    @PostMapping("/addClass")
    public ResponseEntity<FitnessClassResponse> createClass(
            @Valid @RequestBody FitnessClassCreateRequest request
    ) {
        FitnessClassResponse response = classService.createClass(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/id/{idClass}")
    public ResponseEntity

}
