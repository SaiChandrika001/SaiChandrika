package com.sts.admin.controller;

import com.sts.admin.binding.PlanRequest;
import com.sts.admin.binding.PlanResponse;
import com.sts.admin.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/plans")
public class PlanController {

    private PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/savePlan")
    public ResponseEntity<Boolean> savePlan(@RequestBody PlanRequest planBinding) {
        boolean b = planService.SavePlan(planBinding);


        return new ResponseEntity<>(b, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllPlans")
    public ResponseEntity<List<PlanResponse>> getAllPlans(){
        List<PlanResponse> allPlans = planService.getAllPlans();
        return new ResponseEntity<>(allPlans,HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getPlanById/{id}")
    public ResponseEntity<PlanResponse> getPlanById(@PathVariable Integer id) {
        PlanResponse planById = planService.getPlanById(id);
        return new ResponseEntity<>(planById,HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllCategories")
    public ResponseEntity<Map<Integer, String>> getAllCategories() {
        Map<Integer, String> allPlanCategerious = planService.getAllPlanCategerious();
        return new ResponseEntity<>(allPlanCategerious, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatePlan/{id}")
    public ResponseEntity<Boolean> updatePlan(@PathVariable Integer id,@RequestBody PlanRequest request) {

        boolean b = planService.updatePlan(id,request);
        return new ResponseEntity<>(b, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletePlan/{id}")
    public ResponseEntity<Boolean> deletePlan(@PathVariable Integer id) {
        boolean b = planService.deletePlan(id);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/planStatus/{id}")
    public ResponseEntity<Boolean> planStatus(@PathVariable Integer id,String status) {
        boolean b = planService.planStatusChange(id, status);
        return new ResponseEntity<>(b, HttpStatus.OK);

    }

}
