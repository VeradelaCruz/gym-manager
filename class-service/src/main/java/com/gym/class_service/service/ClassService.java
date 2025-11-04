package com.gym.class_service.service;

import com.gym.class_service.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassService {
    @Autowired
    ClassRepository classRepository;
}
