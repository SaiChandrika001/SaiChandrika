package com.insurance.ed.controller;

import com.insurance.ed.binding.BenefitDetails;
import com.insurance.ed.binding.EligibilityDetailsResponse;
import com.insurance.ed.service.EligibilityDeterminationService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/eligibility")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EligibilityDeterminationController {

    private final EligibilityDeterminationService eligibilityDeterminationService;

    @GetMapping
    public ResponseEntity<List<BenefitDetails>> findAllBenefitDetails() {
        List<BenefitDetails> benefitDetailsList = eligibilityDeterminationService.findAllEligibility();
        if (benefitDetailsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(benefitDetailsList);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BenefitDetails>> searchBenefitDetails(
            @RequestParam(required = false) String planName,
            @RequestParam(required = false) String planStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate planStartDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate planEndDate) {

        List<BenefitDetails> benefitDetailsList = eligibilityDeterminationService.searchBenefitDetails(
                planName, planStatus, planStartDate, planEndDate);

        if (benefitDetailsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(benefitDetailsList);
    }

    @GetMapping("/plan-names")
    public ResponseEntity<List<String>> getDistinctPlanNames() {
        List<String> planNames = eligibilityDeterminationService.getDistinctPlanNames();
        if (planNames.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(planNames);
    }

    @GetMapping("/plan-statuses")
    public ResponseEntity<List<String>> getDistinctPlanStatuses() {
        List<String> planStatuses = eligibilityDeterminationService.getDistinctPlanStatuses();
        if (planStatuses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(planStatuses);
    }

    @GetMapping("/by-ssn")
    public ResponseEntity<List<BenefitDetails>> findByHolderSSN(@RequestParam String ssn) {
        List<BenefitDetails> benefitDetailsList = eligibilityDeterminationService.findByHolderSSN(ssn);
        if (benefitDetailsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(benefitDetailsList);
    }

    @GetMapping("/by-case-number")
    public ResponseEntity<BenefitDetails> findByCaseNumberForReports(@RequestParam Long caseNumber) {
        BenefitDetails benefitDetails = eligibilityDeterminationService.findBenefitDetailsByCaseNumber(caseNumber);
        return ResponseEntity.ok(benefitDetails);
    }

    @GetMapping("/{caseNumber}")
    public ResponseEntity<EligibilityDetailsResponse> determineEligibility(@PathVariable("caseNumber") @NotNull @Positive Long caseNumber) {
        EligibilityDetailsResponse eligibilityResponse = eligibilityDeterminationService.determineEligibility(caseNumber);
        return ResponseEntity.ok(eligibilityResponse);
    }

    @GetMapping("/el/{caseNumber}")
    public ResponseEntity<EligibilityDetailsResponse> getEligibilityByCaseNumber(@PathVariable("caseNumber") @NotNull @Positive Long caseNumber) {
        EligibilityDetailsResponse byCaseNumber = eligibilityDeterminationService.findByCaseNumber(caseNumber);
        return ResponseEntity.ok(byCaseNumber);
    }
}