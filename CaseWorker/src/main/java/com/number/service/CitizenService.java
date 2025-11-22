package com.number.service;

import java.util.List;

import com.number.binding.CitizenRequest;
import com.number.binding.CitizenResponse;

public interface CitizenService {

	
	  CitizenResponse registerCitizen(CitizenRequest request);
	  
	   boolean deleteCitizen(Integer id);
	  
	   List<CitizenResponse> getAllCitizens();
	   
	   public List<CitizenResponse> updateCitizen(CitizenRequest request,Integer id);
	   
	   
	   
}
