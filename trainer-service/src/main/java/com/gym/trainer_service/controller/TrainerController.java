package com.gym.trainer_service.controller;

import com.gym.trainer_service.dtos.TrainerDTO;
import com.gym.trainer_service.dtos.TrainerRequest;
import com.gym.trainer_service.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    @Autowired
    TrainerService trainerService;

    @PostMapping("/add")
    public ResponseEntity<TrainerDTO> addMember(
            @Valid @RequestBody TrainerRequest trainerRequest){
        TrainerDTO trainer = trainerService.createTrainer(trainerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(trainer);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrainerDTO>> getAll(){
        return ResponseEntity.ok(
                trainerService.getAll()
        );
    }

    @GetMapping("/id/{idTrainer}")
    public ResponseEntity<TrainerDTO> getById(@PathVariable String idTrainer){
        return ResponseEntity.ok(trainerService.getById(idTrainer));
    }

    @DeleteMapping("/delete/{idTrainer}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable String idTrainer){
        trainerService.deleteById(idTrainer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{idTrainer}")
    public ResponseEntity<TrainerDTO> updateMember(
            @PathVariable String idTrainer,
            @Valid @RequestBody TrainerRequest request){
        TrainerDTO trainer = trainerService.changeTrainer(idTrainer, request);
        return ResponseEntity.ok(trainer);
    }
}
