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

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public ClassWithTrainer findClassWithTrainer(String idClass) {
        // Buscar la clase
        FitnessClassResponse classResponse = getClassById(idClass);

        // Verificar que la clase tenga un entrenador asignado
        if (classResponse.getTrainer() == null || classResponse.getTrainer().isBlank()) {
            throw new ClassWithTrainerNotFound(classResponse.getIdClass(), "N/A");
        }

        // Llamar al microservicio de entrenadores
        TrainerDTO trainerDTO;
        try {
            trainerDTO = trainerClient.getById(classResponse.getTrainer()).getBody();
            if (trainerDTO == null) {
                // Esto es opcional, por si el cuerpo viene vacío
                throw new TrainerNotFound( classResponse.getTrainer());
            }
        } catch (FeignException.NotFound e) {
            throw new TrainerNotFound( classResponse.getTrainer());
        } catch (FeignException e) {
            throw new RuntimeException("Error calling trainer-service: " + e.getMessage());
        }

        // Armar y devolver DTO
        return ClassWithTrainer.builder()
                .idClass(idClass)
                .name(classResponse.getName())
                .maxParticipants(classResponse.getMaxParticipants())
                .durationMinutes(classResponse.getDurationMinutes())
                .scheduleDateTime(classResponse.getScheduleDateTime())
                .trainerDTO(trainerDTO)
                .build();
    }

    //Get clases by schedule:
    public List<ClassWithSchedule> findClassWithSchedule(LocalTime time){
        return classRepository.findAll().stream()
                .filter(c -> c.getScheduleDateTime().toLocalTime().equals(time))
                .map(mapper::toDto) // Usando el mapper
                .toList();
    }







}
