package com.demo.binding;

import java.time.Instant;

import lombok.Data;

@Data
public class ClaimResponse {

	private Integer id;
	private String policyNumber;
	private String claimantName;
	private String description;
	private String status;
	private Instant createdAt;
	private Instant updatedAt;

}
