package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.ApplicationEntity;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
	
	
}