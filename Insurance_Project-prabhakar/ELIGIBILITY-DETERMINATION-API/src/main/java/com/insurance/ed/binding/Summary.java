package com.insurance.ed.binding;

import lombok.Data;

import java.util.List;

@Data
public class Summary {

    private IncomeRequest incomeRequest;

    private EducationRequest educationRequest;

    private List<ChildRequest> childrenRequest;

    private String planName;

    private Integer applicationId;
}
