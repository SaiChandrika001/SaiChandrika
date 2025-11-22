package com.sts.admin.service;

import com.sts.admin.binding.PlanCateroryBinding;
import com.sts.admin.entity.PlanCategory;
import com.sts.admin.repository.PlanCategoryRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PlanCategoryServiceImpl implements PlanCategoryService{

    private PlanCategoryRepo categoryRepo;

    public PlanCategoryServiceImpl(PlanCategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public PlanCategory savePlanCategory(PlanCateroryBinding cateroryBinding) {
        PlanCategory planCategory = new PlanCategory();
        BeanUtils.copyProperties(cateroryBinding, planCategory);
        PlanCategory save = categoryRepo.save(planCategory);
        return save;
    }
}
