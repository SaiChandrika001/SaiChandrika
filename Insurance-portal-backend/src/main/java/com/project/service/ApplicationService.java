package com.project.service;

import java.util.List;

import com.project.dao.ApplicationRequest;
import com.project.entity.ApplicationEntity;

public interface ApplicationService {
	
	ApplicationEntity createApplication(ApplicationRequest request);

    List<ApplicationEntity> getAllApplications();

}
