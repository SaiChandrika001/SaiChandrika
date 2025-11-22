package com.sts.admin.repository;

import com.sts.admin.entity.PlanCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanCategoryRepo  extends CrudRepository<PlanCategory,Integer> {
}
