package com.insurance.ed.binding;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EligibilityDetailsResponse {

    private String planName;

    private String planStatus;

    private LocalDate planStartDate;

    private LocalDate planEndDate;

    private Double benefitAmt;

    private String denialReason;
}
