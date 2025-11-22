package com.sts.admin.controller;

import com.sts.admin.binding.CaseWorkerRequest;
import com.sts.admin.binding.CaseWorkerResponse;
import com.sts.admin.service.CaseWorkerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/caseworker")
@Validated
public class CaseWorkerController {

    private CaseWorkerService caseWorkerService;


    public CaseWorkerController(CaseWorkerService caseWorkerService) {
        this.caseWorkerService = caseWorkerService;


    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CaseWorkerResponse> createCaseWorker(@Valid @RequestBody CaseWorkerRequest request){
        CaseWorkerResponse caseWorker = caseWorkerService.createCaseWorker(request);
        return new ResponseEntity<>(caseWorker, HttpStatus.CREATED);

    }
    @PreAuthorize("hasAnyRole('ADMIN','CASEWORKER')")
    @GetMapping("/{id}")
    public ResponseEntity<CaseWorkerResponse> getCaseWorker(@PathVariable("id") Long id){
        CaseWorkerResponse caseWorker = caseWorkerService.getCaseWorker(id);
        return new ResponseEntity<>(caseWorker,HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<CaseWorkerResponse>> getAllCaseWorkers(){
        List<CaseWorkerResponse> allCaseWorkers = caseWorkerService.getAllCaseWorkers();
        return new ResponseEntity<>(allCaseWorkers,HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<CaseWorkerResponse> updateCaseWorker(@Valid @PathVariable("id") Long id,@RequestBody CaseWorkerRequest request){
        CaseWorkerResponse response = caseWorkerService.updateCaseWorker(id, request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteCaseWorker(@PathVariable ("id") Long id){
        boolean b = caseWorkerService.deleteCaseWorker(id);
        return new ResponseEntity<>(b,HttpStatus.OK);

    }


}
