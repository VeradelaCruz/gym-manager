package com.gym.class_service.service;

import com.gym.class_service.dtos.*;
import com.gym.class_service.exceptions.ClassNotFound;
import com.gym.class_service.exceptions.ClassWithTrainerNotFound;
import com.gym.class_service.exceptions.TrainerNotFound;
import com.gym.class_service.feign.TrainerClient;
import com.gym.class_service.mapper.FitnessClassMapper;
import com.gym.class_service.models.FitnessClass;
import com.gym.class_service.repository.ClassRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private FitnessClassMapper mapper;

    @Autowired
    private TrainerClient trainerClient;
    /// --- CRUD OPERATIONS ----

    //Create
    public FitnessClassResponse createClass(FitnessClassCreateRequest dto){
        FitnessClass entity = mapper.toEntity(dto);
        FitnessClass savedEntity = classRepository.save(entity);
        return mapper.toResponse(savedEntity);
    }

    //FindAll
    public List<FitnessClassResponse> getAllClasses() {
        return classRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    //FindById
    public FitnessClassResponse getClassById(String idClass) {
        FitnessClass entity = classRepository.findById(idClass)
                .orElseThrow(() -> new ClassNotFound(idClass));
        return mapper.toResponse(entity);
    }


    //Delete
    public void deleteClass(String id) {
        FitnessClass existing = classRepository.findById(id)
                .orElseThrow(() -> new ClassNotFound(id));
        classRepository.delete(existing);
    }

    //Update
    public FitnessClassResponse updateClass(String id, FitnessClassUpdateRequest request) {
        FitnessClass existing = classRepository.findById(id)
                .orElseThrow(() -> new ClassNotFound(id));

        mapper.updateFromDTO(request, existing); // 👈 Actualiza solo los campos no nulos

        FitnessClass updated = classRepository.save(existing);
        return mapper.toResponse(updated);
    }

    /// ----OTHER OPERATIONS----

    //Get Class with Trainer
    public ClassWithTrainer findClassWithTrainer(String idClass) {
        //Buscar la clase
        FitnessClassResponse classResponse = getClassById(idClass);

        TrainerDTO trainerDTO = null;

        // Verificar si la clase tiene entrenador asignado
        if (classResponse.getTrainer() == null || classResponse.getTrainer().isBlank()) {
            throw new ClassWithTrainerNotFound(classResponse.getIdClass(), "N/A");
        }

        try {
            // Llamar al microservicio de entrenadores
            trainerDTO = trainerClient.getById(classResponse.getTrainer()).getBody();
        } catch (FeignException.NotFound e) {
            // Si el entrenador no existe en el microservicio remoto
            throw new TrainerNotFound("Trainer not found for id " + classResponse.getTrainer());
        }


        //Armar el dto
        return ClassWithTrainer.builder()
                .idClass(idClass)
                .name(classResponse.getName())
                .maxParticipants(classResponse.getMaxParticipants())
                .durationMinutes(classResponse.getDurationMinutes())
                .scheduleDateTime(classResponse.getScheduleDateTime())
                .trainerDTO(trainerDTO)
                .build();
    }







}
