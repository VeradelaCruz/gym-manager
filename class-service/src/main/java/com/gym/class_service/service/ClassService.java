package com.gym.class_service.service;

import com.gym.class_service.models.FitnessClass;
import com.gym.class_service.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;
    /// --- CRUD OPERATIONS ----

    public List<FitnessClass> findAll(){
        return classRepository.findAll();
    }

    private FitnessClass findById(String idClass){
        return classRepository.findById(idClass)
                .orElseThrow(()-> new )
    }
}
