package com.example.module;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="SSN_Data")
public class SSN {

	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="FullName")
	private String fullName;
	
	@Column(name="SSN")
	private long ssn;
	
	@Column(name="StateName")
	private String StateName;
}
