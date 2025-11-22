package com.insurance.datacollection.controller;

import com.insurance.datacollection.binding.DCCaseResponse;
import com.insurance.datacollection.service.DataCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/case")
@RequiredArgsConstructor
public class CaseController {

    private final DataCollectionService dataCollectionService;

    @PostMapping("/{applicationId}")
    public ResponseEntity<Long> loadCaseNumber(@PathVariable Integer applicationId) {
        Long caseNumber = dataCollectionService.loadCaseNumber(applicationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(caseNumber);
    }

    @GetMapping(path = "/{caseNumber}")
    public ResponseEntity<DCCaseResponse> findByCaseNumber(@PathVariable Long caseNumber) {
        DCCaseResponse byCaseNumber = dataCollectionService.findByCaseNumber(caseNumber);
        return ResponseEntity.status(HttpStatus.OK).body(byCaseNumber);
    }
}