package com.insurance.datacollection.controller;

import com.insurance.datacollection.binding.IncomeRequest;
import com.insurance.datacollection.service.DataCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {

    private final DataCollectionService dataCollectionService;

    @PostMapping
    public ResponseEntity<Long> saveIncomeData(@RequestBody IncomeRequest incomeRequest) {
        Long caseNumber = dataCollectionService.saveIncomeData(incomeRequest);
        return ResponseEntity.ok(caseNumber);
    }
}