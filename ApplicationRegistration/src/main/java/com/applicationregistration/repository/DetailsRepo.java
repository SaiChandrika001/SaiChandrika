package com.applicationregistration.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.applicationregistration.modal.Details;

public interface DetailsRepo extends JpaRepository<Details,Integer>{
	
  @Querry("select distinct(planName) form Details ")
   public List<String>findPlanNames();
	
  @Querry("select distinct(planStatus) form Details ")
  public List<String>findPlanStatus();
	
}
