package com.sts.admin.service;

import com.sts.admin.binding.PlanRequest;
import com.sts.admin.binding.PlanResponse;

import java.util.List;
import java.util.Map;

public interface PlanService {

    List<PlanResponse> getAllPlans();

    Map<Integer,String> getAllPlanCategerious();

    boolean SavePlan(PlanRequest planRequest);

    PlanResponse getPlanById(Integer PlanId);

    boolean updatePlan(Integer planId, PlanRequest planRequest);

    boolean deletePlan(Integer PlanId);

    boolean planStatusChange(Integer PlanId,String activeStatus);
}
