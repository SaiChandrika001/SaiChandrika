package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "claims")
public class Claim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "policy_number", nullable = false)
	private String policyNumber;

	@Column(name = "claimant_name", nullable = false)
	private String claimantName;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	private String status;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;

	@PrePersist
	public void onCreate() {
		Instant now = Instant.now();
		this.createdAt=now;
		this.updatedAt=now;
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = Instant.now();
	}

}
