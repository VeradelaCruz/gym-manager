package com.gym.class_service.controller;

import com.gym.class_service.dtos.FitnessClassCreateRequest;
import com.gym.class_service.dtos.FitnessClassResponse;
import com.gym.class_service.dtos.FitnessClassUpdateRequest;
import com.gym.class_service.service.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @PostMapping("/addClass")
    public ResponseEntity<FitnessClassResponse> createClass(
            @Valid @RequestBody FitnessClassCreateRequest request
    ) {
        FitnessClassResponse response = classService.createClass(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FitnessClassResponse>> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }

    @GetMapping("/id/{idClass}")
    public ResponseEntity<FitnessClassResponse> getClassById(@PathVariable String idClass) {
        return ResponseEntity.ok(classService.getClassById(idClass));
    }

    @DeleteMapping("/delete/{idClass}")
    public ResponseEntity<Void> deleteClass(@PathVariable String idClass) {
        classService.deleteClass(idClass);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FitnessClassResponse> updateClass(
            @PathVariable String id,
            @Valid @RequestBody FitnessClassUpdateRequest request
    ) {
        FitnessClassResponse updated = classService.updateClass(id, request);
        return ResponseEntity.ok(updated);
    }
}
