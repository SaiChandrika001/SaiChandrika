package com.insurance.ed.binding;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BenefitDetails {

    private Long caseNumber;

    private String holderName;

    private String holderSSN;

    private String planName;

    private String planStatus;

    private LocalDate planStartDate;

    private LocalDate planEndDate;

    private Double benefitAmount;

    private String denialReason;
}
