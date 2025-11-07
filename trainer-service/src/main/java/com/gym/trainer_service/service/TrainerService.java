package com.gym.trainer_service.service;

import com.gym.trainer_service.dtos.TrainerDTO;
import com.gym.trainer_service.dtos.TrainerRequest;
import com.gym.trainer_service.dtos.TrainerUpdateRequest;
import com.gym.trainer_service.exception.TrainerNotFound;
import com.gym.trainer_service.mapper.TrainerMapper;
import com.gym.trainer_service.models.Trainer;
import com.gym.trainer_service.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;

@Service
public class TrainerService {
    @Autowired
    public TrainerRepository trainerRepository;

    @Autowired
    public TrainerMapper mapper;

    /// ----CRUD OPERATIONS---
    //Create
    public TrainerDTO createTrainer(TrainerRequest request){
        Trainer trainer= mapper.toEntity(request);
        trainerRepository.save(trainer);
        return  mapper.toDto(trainer);
    }

    //Read all
    public List<TrainerDTO> getAll(){
        return trainerRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    //Read by id
    public TrainerDTO getById(String idTrainer){
        Trainer found= trainerRepository.findById(idTrainer)
                .orElseThrow(()-> new TrainerNotFound(idTrainer));
        return mapper.toDto(found);
    }

    //Delete
    public void deleteById(String idTrainer){
        Trainer existing= trainerRepository.findById(idTrainer)
                .orElseThrow(()-> new TrainerNotFound(idTrainer));
        trainerRepository.deleteById(existing.getIdTrainer());
    }

    //Update
    public TrainerDTO changeTrainer(String idTrainer, TrainerUpdateRequest updateRequest){
        Trainer trainer= trainerRepository.findById(idTrainer)
                .orElseThrow(()-> new TrainerNotFound(idTrainer));
        mapper.updateFromDto(updateRequest, trainer);
        Trainer updated= trainerRepository.save(trainer);

        return mapper.toDto(updated);
    }
}
