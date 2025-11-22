package com.sts.admin.service;

import com.sts.admin.Exception.*;
import com.sts.admin.binding.PlanRequest;
import com.sts.admin.binding.PlanResponse;
import com.sts.admin.entity.Plan;
import com.sts.admin.entity.PlanCategory;
import com.sts.admin.repository.PlanCategoryRepo;
import com.sts.admin.repository.PlanRepo;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
//import java.util.stream.Collectors;


@Service
public class PlanServiceImpl implements PlanService{


    private PlanRepo planRepo;

    private PlanCategoryRepo planCategoryRepo;

    public PlanServiceImpl(PlanRepo planRepo, PlanCategoryRepo planCategoryRepo) {
        this.planRepo = planRepo;
        this.planCategoryRepo = planCategoryRepo;
    }


    @Override
    public Map<Integer, String> getAllPlanCategerious() {
        Iterable<PlanCategory>  categories= planCategoryRepo.findAll();
        List<PlanCategory> categoriesList=new ArrayList<>();

        // For Java 7 Code by Using Enhanced For Loop
      /*  for (PlanCategory c : categories) {
            categoriesList.add(c);
        }*/
        categories.forEach(c->categoriesList.add(c));
        if(categoriesList.isEmpty()){
            throw  new CategoryNotFoundException("Category is Not Found");
        }
        Map<Integer, String> categoriesMap=new HashMap<>();
        categoriesList.forEach(planCategory->{
            categoriesMap.put(planCategory.getPlanCategoryId(), planCategory.getPlanCategoryName());
        });
        return categoriesMap;
//        return categoriesList.stream().collect(Collectors.toMap(PlanCategory::getPlanCategoryId,PlanCategory::getPlanCategoryName));
    }

    @Override
    public List<PlanResponse> getAllPlans() {
        List<Plan> all = planRepo.findAll();
/*        List<PlanResponse> allResponse=new ArrayList<>();
        for(Plan plan : all){
            PlanResponse planResponse=new PlanResponse();
            BeanUtils.copyProperties(plan,planResponse);
               allResponse.add(planResponse);

        }
        return allResponse;*/
        if(all.isEmpty()){
            throw new PlanNotFoundException("No Plan is Found");

        }
        return all.stream().map(this::convertToResponse).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public boolean SavePlan(PlanRequest planRequest) {
        ValidatePlanDates(planRequest.getPlanStartDate(), planRequest.getPlanEndDate());

        Plan plan = new Plan();
        BeanUtils.copyProperties(planRequest, plan);

        if (plan.getActiveSw() == null) {
            plan.setActiveSw("NO");
        }

        PlanCategory category = planCategoryRepo.findById(planRequest.getPlanCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with id: " + planRequest.getPlanCategoryId()));

        plan.setCategory(category);

        try {
            planRepo.save(plan);
            return true;
        } catch (Exception e) {
            throw new UnableToSaveException("Failed to save plan :" + e.getMessage());
        }
    }


    @Override
    public PlanResponse getPlanById(Integer planId) {
//        Plan planById = findPlanById(planId);
//        return convertToResponse(planById);
        Optional<Plan> byId = planRepo.findById(planId);
        if(byId.isPresent()){
            Plan plan = byId.get();
            PlanResponse planResponse = new PlanResponse();
            BeanUtils.copyProperties(plan, planResponse);
            return planResponse;
        }

     throw new PlanNotFoundException("Plan not found with id: " + planId);
    }

    @Override
    public boolean updatePlan(Integer planId, PlanRequest planRequest) {
        Plan plan = findPlanById(planId);
          LocalDate startDate = planRequest.getPlanStartDate()!=null?planRequest.getPlanStartDate():planRequest.getPlanStartDate();
          LocalDate endDate=planRequest.getPlanEndDate()!=null?planRequest.getPlanEndDate():planRequest.getPlanEndDate();
          ValidatePlanDates(startDate, endDate);
          updatePlanFields(plan,planRequest);
        PlanCategory category = findPlanCategoryById(planId);
        plan.setCategory(category);
        try{
            planRepo.save(plan);
            return true;

        }catch (Exception e){
            throw  new UnableToUpdateException("Failed to update plan :" +e.getMessage());

        }
    }

    private void updatePlanFields(Plan plan, PlanRequest planRequest) {
        if(planRequest.getPlanName()!=null){
            plan.setPlanName(planRequest.getPlanName());
        }
        if(planRequest.getPlanStartDate()!=null){
            plan.setPlanStartDate(planRequest.getPlanStartDate());

        }if(planRequest.getPlanEndDate()!=null){
            plan.setPlanEndDate(planRequest.getPlanEndDate());
        }
        if(planRequest.getActiveSw()!=null){
            plan.setActiveSw(planRequest.getActiveSw());
        }

    }

    @Override
    public boolean deletePlan(Integer PlanId) {
        if (!planRepo.existsById(PlanId)) {
            throw new PlanNotFoundException("Plan Not Found with id :" + PlanId);
        }
            try {
                planRepo.deleteById(PlanId);
                return true;
            } catch (Exception e) {

                throw new UnableToDeleteException("Unable to Delete Plan :" + e.getMessage());
            }

        }

    @Override
    public boolean planStatusChange(Integer PlanId, String activeStatus) {
        Plan planById = findPlanById(PlanId);
        if(!activeStatus.equalsIgnoreCase("YES")&&!activeStatus.equalsIgnoreCase("NO")){
            throw  new StatusException("Status is either Yes or No");
        }
        planById.setActiveSw(activeStatus);
        try {
            planRepo.save(planById);
            return true;
        }catch (Exception e) {
            throw new UnableToUpdateException("Failed to save plan :" + e.getMessage());

        }
    }
    private Plan findPlanById(Integer planId) {
        return planRepo.findById(planId).orElseThrow(()->new PlanNotFoundException("Plan Not Found"));
    }
    private void ValidatePlanDates(LocalDate planStartDate, LocalDate planEndDate) {
        if(planStartDate!= null && planEndDate != null){
            if(planStartDate.isAfter(planEndDate)){
                throw new DateException("Plan Start Date cannot be after Plan End Date.  Start Date:" +planStartDate +" , End Date:" +planEndDate);
            }
            if(planStartDate.isEqual(planEndDate)){
                throw new DateException("Plan Start Date cannot be equal to end date");
            }
        }
    }
    private PlanCategory findPlanCategoryById(Integer planCategoryId) {
        if (planCategoryId == null || planCategoryId <= 0) {
            throw new CategoryNotFoundException("Invalid category id: " + planCategoryId);
        }
        return planCategoryRepo.findById(planCategoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + planCategoryId));
    }

    private PlanResponse convertToResponse(Plan plan) {
        PlanResponse planResponse = new PlanResponse();
        BeanUtils.copyProperties(plan, planResponse);
        if(plan.getCategory()!=null){
            planResponse.setPlanCategoryName(plan.getCategory().getPlanCategoryName());
        }
        return planResponse;
    }
}
