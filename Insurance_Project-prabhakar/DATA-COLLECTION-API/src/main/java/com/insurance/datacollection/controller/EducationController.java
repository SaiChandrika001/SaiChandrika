package com.insurance.datacollection.controller;

import com.insurance.datacollection.binding.EducationRequest;
import com.insurance.datacollection.service.DataCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/education")
@RequiredArgsConstructor
public class EducationController {

    private final DataCollectionService dataCollectionService;

    @PostMapping
    public ResponseEntity<Long> saveEducation(@RequestBody EducationRequest educationRequest) {
        Long caseNumber = dataCollectionService.saveEducation(educationRequest);
        return ResponseEntity.ok(caseNumber);
    }
}