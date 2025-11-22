package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.Claim;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {
	
	List<Claim> findByPolicyNumber(String policyNumber);
	List<Claim> findByStatus(String status);
}
