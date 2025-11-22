package com.insurance.datacollection.controller;

import com.insurance.datacollection.binding.PlanSelectionRequest;
import com.insurance.datacollection.service.DataCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final DataCollectionService dataCollectionService;

    @GetMapping
    public ResponseEntity<Map<Integer, String>> getPlans() {
        Map<Integer, String> plans = dataCollectionService.getPlans();
        return ResponseEntity.ok(plans);
    }

    @PostMapping("/selection")
    public ResponseEntity<Long> savePlanSelection(@RequestBody PlanSelectionRequest planSelectionRequest) {
        Long caseNumber = dataCollectionService.savePlanSelection(planSelectionRequest);
        return ResponseEntity.ok(caseNumber);
    }
}