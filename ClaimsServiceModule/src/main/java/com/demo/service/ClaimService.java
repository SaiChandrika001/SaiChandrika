package com.demo.service;

import java.util.List;

import com.demo.binding.ClaimRequest;
import com.demo.binding.ClaimResponse;

public interface ClaimService {

	ClaimResponse createClaim(ClaimRequest request);

	ClaimResponse getClaimById(Integer id);

	List<ClaimResponse> getAllClaims();

	List<ClaimResponse> getClaimsByPolicy(String policyNumber);

	ClaimResponse updateStatus(Integer id, String status);

	ClaimResponse deleteClaim(Integer id);
}
