package com.insurance.ed.binding;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CitizenAppResponse {

    private Integer citizenApplicationId;
    private String citizenName;
    private String citizenState;
    private String citizenEmail;
    private String citizenMobile;
    private String citizenGender;
    private LocalDate citizenDateOfBirth;
    private String citizenSsn;
}
