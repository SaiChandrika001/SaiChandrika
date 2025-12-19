package com.project.dao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationRequest {
	
	  @NotBlank
	    private String name;

	    @Email
	    private String email;

}
