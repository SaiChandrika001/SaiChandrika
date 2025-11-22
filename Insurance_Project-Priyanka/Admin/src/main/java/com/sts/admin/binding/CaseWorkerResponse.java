package com.sts.admin.binding;

import lombok.Data;

import java.util.Date;

@Data
public class CaseWorkerResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String gender;

    private Date dateOfBirth;

    private String ssnMasked;

    private String ssnStatus;

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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSsnMasked() {
		return ssnMasked;
	}

	public void setSsnMasked(String ssnMasked) {
		this.ssnMasked = ssnMasked;
	}

	public String getSsnStatus() {
		return ssnStatus;
	}

	public void setSsnStatus(String ssnStatus) {
		this.ssnStatus = ssnStatus;
	}
}
