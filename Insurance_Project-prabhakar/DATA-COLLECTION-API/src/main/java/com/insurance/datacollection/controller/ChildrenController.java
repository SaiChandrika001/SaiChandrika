package com.insurance.datacollection.controller;

import com.insurance.datacollection.binding.ChildRequest;
import com.insurance.datacollection.binding.ChildrenRequest;
import com.insurance.datacollection.binding.Summary;
import com.insurance.datacollection.service.DataCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/children")
@RequiredArgsConstructor
public class ChildrenController {

    private final DataCollectionService dataCollectionService;

    @PostMapping
    public ResponseEntity<Summary> saveChildrenData(@RequestBody ChildRequest childRequest) {
        Long caseNumber = dataCollectionService.saveChildrenData(childRequest);
        Summary summary = dataCollectionService.getSummary(caseNumber);
        return ResponseEntity.ok(summary);
    }
}