package com.example.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.module.SSN;
@Repository
public interface SsnRepo extends JpaRepository<SSN, Integer>{

	Optional<SSN> findBySsn(long ssn);
}
