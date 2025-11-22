package com.insurance.datacollection.binding;

import lombok.Data;

@Data
public class DCCaseResponse {

    private Long caseNumber;

    private Integer applicationId;

    private Integer planId;
}
