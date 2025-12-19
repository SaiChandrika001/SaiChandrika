package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.dao.ApplicationRequest;
import com.project.entity.ApplicationEntity;
import com.project.repository.ApplicationRepository;

@Service
public class ApplicationServiceImpl  implements ApplicationService {

    private final ApplicationRepository repository;

    public ApplicationServiceImpl(ApplicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ApplicationEntity createApplication(ApplicationRequest request) {

        ApplicationEntity entity = new ApplicationEntity();
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setStatus("PENDING");

        return repository.save(entity);
        
    }

    @Override
    public List<ApplicationEntity> getAllApplications() {
        return repository.findAll();
    }
    
}