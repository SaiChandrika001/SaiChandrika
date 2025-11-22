package com.demo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.binding.ClaimRequest;
import com.demo.binding.ClaimResponse;
import com.demo.binding.UpdateStatusRequest;
import com.demo.service.ClaimService;

import java.util.List;

@RestController
@RequestMapping("/claims")
public class ClaimController {

	private final ClaimService service;

	public ClaimController(ClaimService service) {
		this.service = service;
	}

	@PostMapping("/create")
	public ResponseEntity<ClaimResponse> createClaim(@Valid @RequestBody ClaimRequest req) {
		ClaimResponse response = service.createClaim(req);
		return new ResponseEntity<ClaimResponse>(response, HttpStatus.CREATED);
	}

	@GetMapping("/getClaim/{id}") 
	public ResponseEntity<ClaimResponse> getClaimById(@PathVariable Integer id) {
		ClaimResponse claimById = service.getClaimById(id);
		return new ResponseEntity<ClaimResponse>(claimById, HttpStatus.OK);
	}

	@GetMapping("/allclaims")
	public ResponseEntity<List<ClaimResponse>> getAllClaims(@RequestParam(required = false) String policyNumber) {
		List<ClaimResponse> claims;
		if (policyNumber != null && !policyNumber.isEmpty()) {
			claims = service.getClaimsByPolicy(policyNumber);
		} else {
			claims = service.getAllClaims();
		}
		return new ResponseEntity<List<ClaimResponse>>(claims, HttpStatus.OK);
	}

	@GetMapping("/test-trace")
	public String testTrace() {
	    System.out.println("Trace test executed");
	    return "Trace Working!";
	}
	


	@PutMapping("/update/{id}")
	public ResponseEntity<ClaimResponse> updateStatusClaim(@PathVariable Integer id,
			@Valid @RequestBody UpdateStatusRequest req) {
		ClaimResponse updatedClaim = service.updateStatus(id, req.getStatus());
		return new ResponseEntity<ClaimResponse>(updatedClaim, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ClaimResponse> deleteClaimById(@PathVariable Integer id) {
		ClaimResponse deletedClaim = service.deleteClaim(id);
		return new ResponseEntity<ClaimResponse>(deletedClaim, HttpStatus.OK);
	}
}
