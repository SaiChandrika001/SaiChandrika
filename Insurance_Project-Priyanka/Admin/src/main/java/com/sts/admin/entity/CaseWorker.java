package com.sts.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name="Case_workers")
@Data
public class CaseWorker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(name = "FULL_NAME")
    @NotBlank
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name="PWD")
    private String password;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

    @Column(name="GENDER")
    private String gender;

    @Column(name="SSN",length=2048)
    private String ssnEncrypted;

    @Column(name="SSN_LAST4",length = 4)
    private String ssnLast4;

    @Column(name="SSN_STATUS")
    private String ssnStatus;

    @Column(name="DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "ACTIVE_SW")
    private String activeSw;

    @Column(name = "CREATED_AT",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT",updatable = false)
    private LocalDateTime updatedAt;

    @Column(name="CREATED_BY")
    private String createdBy;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSsnEncrypted() {
		return ssnEncrypted;
	}

	public void setSsnEncrypted(String ssnEncrypted) {
		this.ssnEncrypted = ssnEncrypted;
	}

	public String getSsnLast4() {
		return ssnLast4;
	}

	public void setSsnLast4(String ssnLast4) {
		this.ssnLast4 = ssnLast4;
	}

	public String getSsnStatus() {
		return ssnStatus;
	}

	public void setSsnStatus(String ssnStatus) {
		this.ssnStatus = ssnStatus;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getActiveSw() {
		return activeSw;
	}

	public void setActiveSw(String activeSw) {
		this.activeSw = activeSw;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "UPDATED_BY")
    private String updatedBy;
}
