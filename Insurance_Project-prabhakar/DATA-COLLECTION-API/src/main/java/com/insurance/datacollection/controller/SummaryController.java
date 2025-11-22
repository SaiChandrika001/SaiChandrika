package com.insurance.datacollection.controller;

import com.insurance.datacollection.binding.Summary;
import com.insurance.datacollection.service.DataCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final DataCollectionService dataCollectionService;

    @GetMapping("/{caseNumber}")
    public ResponseEntity<Summary> getSummary(@PathVariable Long caseNumber) {
        Summary summary = dataCollectionService.getSummary(caseNumber);
        if (summary == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(summary);
    }
}