package com.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.demo.binding.ClaimRequest;
import com.demo.binding.ClaimResponse;
import com.demo.configuration.AdminFeignClient;
import com.demo.entity.Claim;
import com.demo.exception.ResourceNotFoundException;
import com.demo.exception.UnableToCreateClaimException;
import com.demo.exception.UnableToDeleteClaimException;
import com.demo.exception.UnableToFetchClaimsException;
import com.demo.exception.UnableToUpdateClaimException;
import com.demo.repository.ClaimRepository;

@Service
public class ClaimServiceImpl implements ClaimService {

	private final ClaimRepository claimRepository;

	public ClaimServiceImpl(ClaimRepository claimRepository) {
		this.claimRepository = claimRepository;
	}
	
	   
	@Override
	public ClaimResponse createClaim(ClaimRequest request) {
		try {
			Claim claim = new Claim();
			claim.setPolicyNumber(request.getPolicyNumber());
			claim.setClaimantName(request.getClaimantName());
			claim.setDescription(request.getDescription());
			claim.setStatus("OPEN");

			Claim savedClaim = claimRepository.save(claim);
			return convertToResponse(savedClaim);
		} catch (Exception e) {
			throw new UnableToCreateClaimException("Failed to create claim: " + e.getMessage());
		}
	}

	@Override
	public ClaimResponse getClaimById(Integer id) {
		Claim claim = claimRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + id));
		return convertToResponse(claim);
	}

	@Override
	public List<ClaimResponse> getAllClaims() {
		try {
			List<Claim> claimList = claimRepository.findAll();
			if (claimList.isEmpty()) {
				throw new UnableToFetchClaimsException("No claims found");
			}
			return claimList.stream().map(this::convertToResponse).collect(Collectors.toList());
		} catch (Exception e) {
			throw new UnableToFetchClaimsException("Failed to fetch claims: " + e.getMessage());
		}
	}

	@Override
	public List<ClaimResponse> getClaimsByPolicy(String policyNumber) {
		try {
			List<Claim> claimList = claimRepository.findByPolicyNumber(policyNumber);
			if (claimList.isEmpty()) {
				throw new UnableToFetchClaimsException("No claims found for policy: " + policyNumber);
			}
			return claimList.stream().map(this::convertToResponse).collect(Collectors.toList());
		} catch (Exception e) {
			throw new UnableToFetchClaimsException("Failed to fetch claims for policy: " + e.getMessage());
		}
	}

	@Override
	public ClaimResponse updateStatus(Integer id, String status) {
		try {
			Claim claim = claimRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + id));
			claim.setStatus(status);
			Claim updated = claimRepository.save(claim);
			return convertToResponse(updated);
		} catch (Exception e) {
			throw new UnableToUpdateClaimException("Unable to update claim with ID: " + id + ". " + e.getMessage());
		}
	}

	@Override
	public ClaimResponse deleteClaim(Integer id) {
		try {
			Claim claim = claimRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + id));
			ClaimResponse response = convertToResponse(claim);
			claimRepository.delete(claim);
			return response;
		} catch (Exception e) {
			throw new UnableToDeleteClaimException("Unable to delete claim with ID: " + id + ". " + e.getMessage());
		}
	}

	private ClaimResponse convertToResponse(Claim claim) {
		ClaimResponse response = new ClaimResponse();
		response.setId(claim.getId());
		response.setPolicyNumber(claim.getPolicyNumber());
		response.setClaimantName(claim.getClaimantName());
		response.setDescription(claim.getDescription());
		response.setStatus(claim.getStatus());
		response.setCreatedAt(claim.getCreatedAt());
		response.setUpdatedAt(claim.getUpdatedAt());
		return response;
	}
}
