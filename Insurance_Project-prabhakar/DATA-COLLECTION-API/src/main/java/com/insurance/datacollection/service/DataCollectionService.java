package com.insurance.datacollection.service;

import com.insurance.datacollection.binding.*;

import java.util.Map;

public interface DataCollectionService {

    Long loadCaseNumber(Integer applicationId);

    Map<Integer, String> getPlans();

    Long savePlanSelection(PlanSelectionRequest planSelectionRequest);

    Long saveIncomeData(IncomeRequest incomeRequest);

    Long saveEducation(EducationRequest educationRequest);

    Long saveChildrenData(ChildRequest childrenRequest);

    Summary getSummary(Long caseNumber);

    DCCaseResponse findByCaseNumber(Long caseNumber);
}
