package com.applicationregistration.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applicationregistration.modal.Details;
import com.applicationregistration.repository.DetailsRepo;
import com.applicationregistration.request.SearchRequest;
import com.applicationregistration.request.SearchResponse;
@Service
public class DetailsServiceImpl implements DetailsService{
    @Autowired
	private DetailsRepo detailsRepo;
    
	@Override
	public List<String> getUniquePlanNames() {
		Set<String> planNames =new HashSet<>();
		List<Details> findAll =detailsRepo.findAll();
		for(Details de: findAll) {
			planNames.add(de.getPlanName());
		}
	    return detailsRepo.findPlanNames();
	
	}

	@Override
	public List<String> getUniquePlanStatus() {
		// TODO Auto-generated method stub
		return detailsRepo.findPlanStatus();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		List<SearchResponse> response = new ArrayList<>();
		Details querryBuilder =new Details();
		String planeNames=request.getPlanName();
		if(planeNames!=null && planeNames.equals("")) {
			 querryBuilder.setPlanName(planeNames);
		}
		String planStatus=request.getPlanStatus();
		if(planStatus !=null & !planStatus.equals("")) {
			 querryBuilder.setPlanStatus(planStatus);
		}
		LocalDate planStartDate =request.getPlanStartData();
		if(planStartDate !=null) {
			 querryBuilder.setPlanStartDate(planStartDate);
		}
		LocalDate planEndDate =request.getPlanEndData();
		if(planEndDate !=null) {
			querryBuilder.setPlanEndDate(planEnd   Date);
		}
		List<Details> entities =detailsRepo.findAll();
		for(Details entity:entities) {
			SearchResponse rs= new SearchResponse();
			BeanUtils.copyProperties(entity, rs);
			response.add(rs);
		}
		return response;
	}

	@Override
	public void generateExcel(HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generatePdf(HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

}
