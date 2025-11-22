package com.insurance.ed.service;

import com.insurance.ed.binding.BenefitDetails;
import com.insurance.ed.binding.EligibilityDetailsResponse;

import java.time.LocalDate;
import java.util.List;

public interface EligibilityDeterminationService {

    EligibilityDetailsResponse determineEligibility(Long caseNum);

    EligibilityDetailsResponse findByCaseNumber(Long caseNumber);

    List<BenefitDetails> findAllEligibility();

    // Below methods are for REPORTS-API

    List<BenefitDetails> searchBenefitDetails(String planName, String planStatus, LocalDate planStartDate, LocalDate planEndDate);

    List<String> getDistinctPlanNames();

    List<String> getDistinctPlanStatuses();

    List<BenefitDetails> findByHolderSSN(String ssn);

    BenefitDetails findBenefitDetailsByCaseNumber(Long caseNumber);

}
