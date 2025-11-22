package com.insurance.ed.service;

import com.insurance.ed.binding.*;
import com.insurance.ed.constants.AppConstants;
import com.insurance.ed.dao.entities.EligibilityDetails;
import com.insurance.ed.dao.repositories.EligibilityDetailsRepository;
import com.insurance.ed.exception.DataNotFoundException;
import com.insurance.ed.exception.EligibilityProcessingException;
import com.insurance.ed.exception.InvalidDataException;
import com.insurance.ed.exception.ServiceUnavailableException;
import com.insurance.ed.feign.ARInvocation;
import com.insurance.ed.feign.COInvocation;
import com.insurance.ed.feign.DCInvocation;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EligibilityDeterminationImplementation implements EligibilityDeterminationService {

    private final ARInvocation arInvocation;
    private final DCInvocation dcInvocation;
    private final COInvocation coInvocation;
    private final EligibilityDetailsRepository eligibilityDetailsRepository;

    @Override
    @Transactional
    public EligibilityDetailsResponse determineEligibility(Long caseNum) {
        log.info("Starting eligibility determination for case number: {}", caseNum);

        validateInput(caseNum);

        try {
            // Fetch summary data
            Summary summary = fetchSummaryData(caseNum);
            validateSummary(summary);

            // Fetch citizen data
            CitizenAppResponse citizenAppResponse = fetchCitizenData(summary.getApplicationId());

            // Calculate age
            Integer age = calculateAge(citizenAppResponse);

            // Determine eligibility
            EligibilityDetailsResponse eligibilityResponse = executeConditions(summary, summary.getPlanName(), age);

            // Save eligibility details
            saveEligibilityDetails(eligibilityResponse, caseNum, citizenAppResponse);

            // Create correspondence trigger - Don't let this failure break the main flow
            try {
                createCorrespondenceTrigger(caseNum);
            } catch (Exception e) {
                log.warn("Failed to create correspondence trigger for case: {} - continuing with eligibility determination", caseNum, e);
                // Optionally, you could set a flag or add a note to the response indicating correspondence trigger failed
            }

            log.info("Eligibility determination completed for case: {} with status: {}",
                    caseNum, eligibilityResponse.getPlanStatus());

            return eligibilityResponse;

        } catch (DataNotFoundException | InvalidDataException | ServiceUnavailableException e) {
            log.error("Known error during eligibility determination for case: {}", caseNum, e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during eligibility determination for case: {}", caseNum, e);
            throw new EligibilityProcessingException("Failed to determine eligibility for case: " + caseNum);
        }
    }

    @Override
    public EligibilityDetailsResponse findByCaseNumber(Long caseNumber) {
        EligibilityDetails byCaseNumber = eligibilityDetailsRepository.findByCaseNumber(caseNumber);
        if (byCaseNumber == null) {
            throw new DataNotFoundException("Eligibility details not found");
        }
        return mapToResponse(byCaseNumber);
    }

    @Override
    public List<BenefitDetails> findAllEligibility() {
        List<EligibilityDetails> all = eligibilityDetailsRepository.findAll();
        if (all.isEmpty()) {
            throw new DataNotFoundException("Eligibility details not found");
        }
        List<BenefitDetails> benefitDetailsList = new ArrayList<>();
        all.forEach(eligibilityDetails -> {
            BenefitDetails benefitDetails = new BenefitDetails();
            BeanUtils.copyProperties(eligibilityDetails, benefitDetails);
            benefitDetailsList.add(benefitDetails);
        });
        return benefitDetailsList;
    }

    // Below methods are for REPORTS-API

    @Override
    public List<BenefitDetails> searchBenefitDetails(String planName, String planStatus, LocalDate planStartDate, LocalDate planEndDate) {
        log.info("Searching benefit details with filters - planName: {}, planStatus: {}, startDate: {}, endDate: {}",
                planName, planStatus, planStartDate, planEndDate);

        try {
            List<EligibilityDetails> eligibilityDetailsList = eligibilityDetailsRepository.searchByFilters(
                    planName, planStatus, planStartDate, planEndDate);

            if (eligibilityDetailsList.isEmpty()) {
                log.warn("No benefit details found for the given search criteria");
                throw new DataNotFoundException("No benefit details found matching the search criteria");
            }

            return eligibilityDetailsList.stream()
                    .map(this::mapToBenefitDetails)
                    .collect(Collectors.toList());

        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while searching benefit details", e);
            throw new EligibilityProcessingException("Failed to search benefit details: " + e.getMessage());
        }
    }

    @Override
    public List<String> getDistinctPlanNames() {
        log.info("Fetching all distinct plan names");

        try {
            List<String> planNames = eligibilityDetailsRepository.findDistinctPlanNames();

            if (planNames.isEmpty()) {
                log.warn("No plan names found in the system");
                throw new DataNotFoundException("No plan names found in the system");
            }

            log.info("Found {} distinct plan names", planNames.size());
            return planNames;

        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while fetching distinct plan names", e);
            throw new EligibilityProcessingException("Failed to fetch plan names: " + e.getMessage());
        }
    }

    @Override
    public List<String> getDistinctPlanStatuses() {
        log.info("Fetching all distinct plan statuses");

        try {
            List<String> planStatuses = eligibilityDetailsRepository.findDistinctPlanStatuses();

            if (planStatuses.isEmpty()) {
                log.warn("No plan statuses found in the system");
                throw new DataNotFoundException("No plan statuses found in the system");
            }

            log.info("Found {} distinct plan statuses", planStatuses.size());
            return planStatuses;

        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while fetching distinct plan statuses", e);
            throw new EligibilityProcessingException("Failed to fetch plan statuses: " + e.getMessage());
        }
    }

    @Override
    public List<BenefitDetails> findByHolderSSN(String ssn) {
        log.info("Searching benefit details for SSN: {}", ssn);

        if (ssn == null || ssn.trim().isEmpty()) {
            throw new InvalidDataException("SSN cannot be null or empty");
        }

        try {
            List<EligibilityDetails> eligibilityDetailsList = eligibilityDetailsRepository.findByHolderSSN(ssn);

            if (eligibilityDetailsList.isEmpty()) {
                log.warn("No benefit details found for SSN: {}", ssn);
                throw new DataNotFoundException("No benefit details found for the provided SSN");
            }

            return eligibilityDetailsList.stream()
                    .map(this::mapToBenefitDetails)
                    .collect(Collectors.toList());

        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while searching benefit details by SSN: {}", ssn, e);
            throw new EligibilityProcessingException("Failed to search benefit details by SSN: " + e.getMessage());
        }
    }

    @Override
    public BenefitDetails findBenefitDetailsByCaseNumber(Long caseNumber) {
        log.info("Fetching benefit details for case number: {}", caseNumber);

        if (caseNumber == null || caseNumber <= 0) {
            throw new InvalidDataException("Case number must be a positive number");
        }

        try {
            EligibilityDetails eligibilityDetails = eligibilityDetailsRepository.findByCaseNumber(caseNumber);

            if (eligibilityDetails == null) {
                log.warn("No benefit details found for case number: {}", caseNumber);
                throw new DataNotFoundException("No benefit details found for case number: " + caseNumber);
            }

            return mapToBenefitDetails(eligibilityDetails);

        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while fetching benefit details for case: {}", caseNumber, e);
            throw new EligibilityProcessingException("Failed to fetch benefit details for case number: " + e.getMessage());
        }
    }

    // Helper method to map EligibilityDetails to BenefitDetails
    private BenefitDetails mapToBenefitDetails(EligibilityDetails eligibilityDetails) {
        BenefitDetails benefitDetails = new BenefitDetails();
        benefitDetails.setCaseNumber(eligibilityDetails.getCaseNumber());
        benefitDetails.setHolderName(eligibilityDetails.getHolderName());
        benefitDetails.setHolderSSN(eligibilityDetails.getHolderSSN());
        benefitDetails.setPlanName(eligibilityDetails.getPlanName());
        benefitDetails.setPlanStatus(eligibilityDetails.getPlanStatus());
        benefitDetails.setPlanStartDate(eligibilityDetails.getPlanStartDate());
        benefitDetails.setPlanEndDate(eligibilityDetails.getPlanEndDate());
        benefitDetails.setBenefitAmount(eligibilityDetails.getBenefitAmount());
        benefitDetails.setDenialReason(eligibilityDetails.getDenialReason());
        return benefitDetails;
    }


    private void validateInput(Long caseNum) {
        if (caseNum == null) {
            throw new IllegalArgumentException("Case number cannot be null");
        }
        if (caseNum <= 0) {
            throw new IllegalArgumentException("Case number must be positive");
        }
    }

    private Summary fetchSummaryData(Long caseNum) {
        try {
            log.debug("Fetching summary data for case: {}", caseNum);
            ResponseEntity<Summary> response = dcInvocation.getSummary(caseNum);
            Summary summary = response.getBody();

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new DataNotFoundException("No data found for case: " + caseNum);
            }

            if (summary == null) {
                throw new DataNotFoundException("Summary data not found for case number: " + caseNum);
            }

            return summary;
        } catch (FeignException e) {
            log.error("External service error while fetching summary for case: {}", caseNum, e);
            throw new ServiceUnavailableException("Unable to fetch case summary from data collection service");
        }
    }

    private void validateSummary(Summary summary) {
        if (summary.getApplicationId() == null) {
            throw new InvalidDataException("Application ID is missing in summary data");
        }
        if (summary.getPlanName() == null || summary.getPlanName().trim().isEmpty()) {
            throw new InvalidDataException("Plan name is missing in summary data");
        }
        if (summary.getIncomeRequest() == null) {
            throw new InvalidDataException("Income information is required for eligibility determination");
        }
    }

    private CitizenAppResponse fetchCitizenData(Integer applicationId) {
        try {
            log.debug("Fetching citizen data for application ID: {}", applicationId);
            ResponseEntity<CitizenAppResponse> response = arInvocation.findCitizenByApplicationId(applicationId);
            CitizenAppResponse citizenAppResponse = response.getBody();

            if (citizenAppResponse == null) {
                throw new DataNotFoundException("Citizen data not found for application ID: " + applicationId);
            }

            return citizenAppResponse;
        } catch (FeignException e) {
            log.error("External service error while fetching citizen data for application: {}", applicationId, e);
            throw new ServiceUnavailableException("Unable to fetch citizen information from application registration service");
        }
    }

    private Integer calculateAge(CitizenAppResponse citizenAppResponse) {
        if (citizenAppResponse.getCitizenDateOfBirth() == null) {
            throw new InvalidDataException("Date of birth is required for eligibility determination");
        }

        LocalDate dateOfBirth = citizenAppResponse.getCitizenDateOfBirth();
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private EligibilityDetailsResponse executeConditions(Summary summary, String planName, Integer age) {
        log.debug("Executing eligibility conditions for plan: {}", planName);

        EligibilityDetailsResponse response = new EligibilityDetailsResponse();
        response.setPlanName(planName);

        switch (planName) {
            case AppConstants.SNAP:
                return evaluateSnapEligibility(summary, response);
            case AppConstants.CCAP:
                return evaluateCcapEligibility(summary, response);
            case AppConstants.MEDICARE:
                return evaluateMedicareEligibility(age, response);
            case AppConstants.MEDICAID:
                return evaluateMedicaidEligibility(summary, age, response);
            case AppConstants.NJW:
                return evaluateNjwEligibility(summary, response);
            default:
                log.warn("Unknown plan type: {}", planName);
                response.setPlanStatus(AppConstants.DENIED);
                response.setDenialReason("Unknown plan type: " + planName);
                return response;
        }
    }

    private EligibilityDetailsResponse evaluateSnapEligibility(Summary summary, EligibilityDetailsResponse response) {
        IncomeRequest incomeRequest = summary.getIncomeRequest();
        Double salaryIncome = incomeRequest.getSalaryIncome();

        if (salaryIncome == null) {
            throw new InvalidDataException("Salary income is required for SNAP eligibility");
        }

        if (salaryIncome <= 300) {
            setApprovedStatus(response);
            log.debug("SNAP eligibility approved for salary income: {}", salaryIncome);
        } else {
            response.setPlanStatus(AppConstants.DENIED);
            response.setDenialReason(AppConstants.HIGH_INCOME);
            log.debug("SNAP eligibility denied for high salary income: {}", salaryIncome);
        }

        return response;
    }

    private EligibilityDetailsResponse evaluateCcapEligibility(Summary summary, EligibilityDetailsResponse response) {
        IncomeRequest incomeRequest = summary.getIncomeRequest();
        List<ChildRequest> childrenRequest = summary.getChildrenRequest();

        if (incomeRequest.getSalaryIncome() == null) {
            throw new InvalidDataException("Salary income is required for CCAP eligibility");
        }

        boolean incomeCondition = incomeRequest.getSalaryIncome() <= 300;
        boolean hasChildren = childrenRequest != null && !childrenRequest.isEmpty();
        boolean ageCondition = true;

        if (hasChildren) {
            for (ChildRequest childRequest : childrenRequest) {
                if (childRequest.getChildrenAge() != null && childRequest.getChildrenAge() > 16) {
                    ageCondition = false;
                    break;
                }
            }
        }

        if (incomeCondition && hasChildren && ageCondition) {
            setApprovedStatus(response);
            log.debug("CCAP eligibility approved");
        } else {
            response.setPlanStatus(AppConstants.DENIED);
            response.setDenialReason(AppConstants.CHILD_CONDITION_MISMATCH);
            log.debug("CCAP eligibility denied - income: {}, hasChildren: {}, ageCondition: {}",
                    incomeCondition, hasChildren, ageCondition);
        }

        return response;
    }

    private EligibilityDetailsResponse evaluateMedicareEligibility(Integer age, EligibilityDetailsResponse response) {
        if (age >= 65) {
            setApprovedStatus(response);
            log.debug("Medicare eligibility approved for age: {}", age);
        } else {
            response.setPlanStatus(AppConstants.DENIED);
            response.setDenialReason(AppConstants.AGE_NOT_MATCHED);
            log.debug("Medicare eligibility denied for age: {}", age);
        }

        return response;
    }

    private EligibilityDetailsResponse evaluateMedicaidEligibility(Summary summary, Integer age, EligibilityDetailsResponse response) {
        IncomeRequest incomeRequest = summary.getIncomeRequest();

        if (incomeRequest.getSalaryIncome() == null || incomeRequest.getPropertyIncome() == null) {
            throw new InvalidDataException("Both salary and property income are required for Medicaid eligibility");
        }

        boolean ageCondition = age < 65;
        boolean salaryCondition = incomeRequest.getSalaryIncome() <= 300;
        boolean propertyCondition = incomeRequest.getPropertyIncome() == 0;

        if (ageCondition && salaryCondition && propertyCondition) {
            setApprovedStatus(response);
            log.debug("Medicaid eligibility approved");
        } else {
            response.setPlanStatus(AppConstants.DENIED);
            response.setDenialReason(AppConstants.RULES_NOT_SATISFIED);
            log.debug("Medicaid eligibility denied - age: {}, salary: {}, property: {}",
                    ageCondition, salaryCondition, propertyCondition);
        }

        return response;
    }

    private EligibilityDetailsResponse evaluateNjwEligibility(Summary summary, EligibilityDetailsResponse response) {
        IncomeRequest incomeRequest = summary.getIncomeRequest();
        EducationRequest educationRequest = summary.getEducationRequest();

        if (educationRequest == null) {
            throw new InvalidDataException("Education information is required for NJW eligibility");
        }

        if (incomeRequest.getSalaryIncome() == null) {
            throw new InvalidDataException("Salary income is required for NJW eligibility");
        }

        if (educationRequest.getGraduationYear() == null) {
            throw new InvalidDataException("Graduation year is required for NJW eligibility");
        }

        boolean incomeCondition = incomeRequest.getSalaryIncome() == 0;
        boolean graduationCondition = educationRequest.getGraduationYear() < LocalDate.now().getYear();

        if (incomeCondition && graduationCondition) {
            setApprovedStatus(response);
            log.debug("NJW eligibility approved");
        } else {
            response.setPlanStatus(AppConstants.DENIED);
            response.setDenialReason(AppConstants.RULES_NOT_SATISFIED);
            log.debug("NJW eligibility denied - income: {}, graduation: {}",
                    incomeCondition, graduationCondition);
        }

        return response;
    }

    private void setApprovedStatus(EligibilityDetailsResponse response) {
        response.setPlanStatus(AppConstants.APPROVED);
        response.setPlanStartDate(LocalDate.now());
        response.setPlanEndDate(LocalDate.now().plusMonths(6));
        response.setBenefitAmt(350.00);
    }

    private void saveEligibilityDetails(EligibilityDetailsResponse eligibilityResponse, Long caseNum, CitizenAppResponse citizenAppResponse) {
        try {
            log.debug("Saving eligibility details for case: {}", caseNum);
            EligibilityDetails eligibilityDetails = new EligibilityDetails();
            eligibilityDetails.setBenefitAmount(eligibilityResponse.getBenefitAmt());
            eligibilityDetails.setPlanName(eligibilityResponse.getPlanName());
            eligibilityDetails.setPlanStatus(eligibilityResponse.getPlanStatus());
            eligibilityDetails.setPlanStartDate(eligibilityResponse.getPlanStartDate());
            eligibilityDetails.setPlanEndDate(eligibilityResponse.getPlanEndDate());
            eligibilityDetails.setDenialReason(eligibilityResponse.getDenialReason());
            eligibilityDetails.setCaseNumber(caseNum);
            eligibilityDetails.setHolderName(citizenAppResponse.getCitizenName());
            eligibilityDetails.setHolderSSN(citizenAppResponse.getCitizenSsn());

            eligibilityDetailsRepository.save(eligibilityDetails);
            log.debug("Successfully saved eligibility details for case: {}", caseNum);
        } catch (Exception e) {
            log.error("Failed to save eligibility details for case: {}", caseNum, e);
            throw new EligibilityProcessingException("Failed to save eligibility details");
        }
    }

    private void createCorrespondenceTrigger(Long caseNum) {
        try {
            log.debug("Creating correspondence trigger for case: {}", caseNum);
            CorrespondenceTriggersRequest correspondenceTriggersRequest = new CorrespondenceTriggersRequest();
            correspondenceTriggersRequest.setCaseNumber(caseNum);
            correspondenceTriggersRequest.setTriggerStatus(AppConstants.PENDING);
            coInvocation.saveTriggers(correspondenceTriggersRequest);
            log.debug("Successfully created correspondence trigger for case: {}", caseNum);
        } catch (FeignException e) {
            log.error("Feign client error while creating correspondence trigger for case: {}", caseNum, e);
            System.out.println(e.getMessage());
            throw new EligibilityProcessingException("Failed to create correspondence trigger due to external service error");
        } catch (Exception e) {
            log.error("Failed to create correspondence trigger for case: {}", caseNum, e);
            System.out.println(e.getMessage());
            throw new EligibilityProcessingException("Failed to create correspondence trigger");
        }
    }

    private EligibilityDetailsResponse mapToResponse(EligibilityDetails eligibilityDetails) {
        EligibilityDetailsResponse response = new EligibilityDetailsResponse();
        response.setPlanName(eligibilityDetails.getPlanName());
        response.setPlanStatus(eligibilityDetails.getPlanStatus());
        response.setPlanStartDate(eligibilityDetails.getPlanStartDate());
        response.setPlanEndDate(eligibilityDetails.getPlanEndDate());
        response.setBenefitAmt(eligibilityDetails.getBenefitAmount());
        response.setDenialReason(eligibilityDetails.getDenialReason());
        return response;
    }
}