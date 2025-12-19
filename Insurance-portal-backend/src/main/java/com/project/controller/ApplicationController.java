package com.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dao.ApplicationRequest;
import com.project.entity.ApplicationEntity;
import com.project.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {
	
	  private final ApplicationService service;

	    public ApplicationController(ApplicationService service) {
	        this.service = service;
	    }

	    @PostMapping
	    public ApplicationEntity create(@Valid @RequestBody ApplicationRequest request) {
	        return service.createApplication(request);
	    }

	    @GetMapping
	    public List<ApplicationEntity> getAll() {
	        return service.getAllApplications();
	    }
	}