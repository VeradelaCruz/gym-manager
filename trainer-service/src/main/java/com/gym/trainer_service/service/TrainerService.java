package com.gym.trainer_service.service;

import com.gym.trainer_service.dtos.TrainerDTO;
import com.gym.trainer_service.models.Trainer;
import com.gym.trainer_service.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    @Autowired
    TrainerRepository trainerRepository;

    /// ----CRUD OPERATIONS---
    //Create

    //Read all
    public List<TrainerDTO> getAll(){
        return trainerRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    //Read by id
    public TrainerDTO getById(String idMember){
        Trainer found= trainerRepository.findById(idMember)
                .orElseThrow(()-> new MemberNotFound(idMember));
        return mapper.toDto(found);
    }

    //Delete
    public void deleteById(String idMember){
        Member existing= memberRepository.findById(idMember)
                .orElseThrow(()-> new MemberNotFound(idMember));
        memberRepository.deleteById(existing.getIdMember());
    }
}
