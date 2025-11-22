package com.insurance.datacollection.binding;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CitizenRequest {

    private String fullName;

    private String email;

    private Long mobileNumber;

    private String gender;

    private Long ssn;

    private LocalDate dob;
}
