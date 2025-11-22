package com.insurance.datacollection.binding;

import lombok.Data;

@Data
public class IncomeRequest {

    private Long caseNumber;

    private Double salaryIncome;

    private Double propertyIncome;

    private Double rentIncome;
}
