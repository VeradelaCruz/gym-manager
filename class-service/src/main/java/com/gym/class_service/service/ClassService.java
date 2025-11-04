package com.gym.class_service.service;

import com.gym.class_service.dtos.FitnessClassCreateRequest;
import com.gym.class_service.dtos.FitnessClassDTO;
import com.gym.class_service.dtos.FitnessClassResponse;
import com.gym.class_service.dtos.FitnessClassUpdateRequest;
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

    public FitnessClassResponse createClass(FitnessClassCreateRequest dto){
        FitnessClass entity = mapper.toEntity(dto);
        FitnessClass savedEntity = classRepository.save(entity);
        return mapper.toResponse(savedEntity);
    }

    public List<FitnessClassResponse> getAllClasses() {
        return classRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public FitnessClassResponse getClassById(String id) {
        FitnessClass entity = classRepository.findById(id)
                .orElseThrow(() -> new ClassNotFound(id));
        return mapper.toResponse(entity);
    }

    public void deleteClass(String id) {
        FitnessClass existing = classRepository.findById(id)
                .orElseThrow(() -> new ClassNotFound(id));
        classRepository.delete(existing);
    }

    public FitnessClassResponse updateClass(String id, FitnessClassUpdateRequest request) {
        FitnessClass existing = classRepository.findById(id)
                .orElseThrow(() -> new ClassNotFound(id));

        mapper.updateFromDTO(request, existing); // 👈 Actualiza solo los campos no nulos

        FitnessClass updated = classRepository.save(existing);
        return mapper.toResponse(updated);
    }


}
