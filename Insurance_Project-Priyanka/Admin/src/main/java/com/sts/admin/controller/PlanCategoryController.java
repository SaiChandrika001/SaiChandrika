package com.sts.admin.controller;


import com.sts.admin.binding.PlanCateroryBinding;
import com.sts.admin.entity.PlanCategory;
import com.sts.admin.service.PlanCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanCategoryController {

    private PlanCategoryService planCategoryService;

    public PlanCategoryController(PlanCategoryService planCategoryService) {
        this.planCategoryService = planCategoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addPlanCategory")
    public ResponseEntity<PlanCategory> addPlanCategory(PlanCateroryBinding planCategory) {
        PlanCategory category = planCategoryService.savePlanCategory(planCategory);
        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }
}
