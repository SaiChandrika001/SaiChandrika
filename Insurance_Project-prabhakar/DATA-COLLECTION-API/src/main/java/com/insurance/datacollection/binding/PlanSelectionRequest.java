package com.insurance.datacollection.binding;

import lombok.Data;

@Data
public class PlanSelectionRequest {

    private Integer applicationId;

    private Long caseNumber;

    private String planName;

    private Integer planId;
}