package com.demo.binding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClaimRequest 
{
	@NotBlank(message = "Policy number cannot be blank")
	private String policyNumber;
	@NotBlank(message = "Claimant name cannot be blank")
	private String claimantName;
	@Size(max=400, message = "Description cannot exceed 400 characters")
	private String description;

}
