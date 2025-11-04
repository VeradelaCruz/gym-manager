package com.gym.class_service.service;

import com.gym.class_service.dtos.FitnessClassDTO;
import com.gym.class_service.exceptions.ClassNotFound;
import com.gym.class_service.mapper.FitnessClassMapper;
import com.gym.class_service.models.FitnessClass;
import com.gym.class_service.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private FitnessClassMapper mapper;
    /// --- CRUD OPERATIONS ----

    public FitnessClass createClass(FitnessClass fitnessClass){
        return classRepository.save(fitnessClass);
    }
    public List<FitnessClass> findAll(){
        return classRepository.findAll();
    }

    public FitnessClass findById(String idClass){
        return classRepository.findById(idClass)
                .orElseThrow(()-> new ClassNotFound(idClass));
    }

    void deleteById(String idClass){
        try {
            classRepository.deleteById(idClass);
        } catch (Exception e) {
            throw new ClassNotFound(idClass);
        }
    }

    public FitnessClassDTO changeClass(String idClass, FitnessClassDTO dto){
        FitnessClass  fitnessClass =findById( idClass);

        mapper.updateFromDTO(dto, fitnessClass);
        FitnessClass saved= classRepository.save(fitnessClass);

        return mapper.toDTO(saved);

    }


}
