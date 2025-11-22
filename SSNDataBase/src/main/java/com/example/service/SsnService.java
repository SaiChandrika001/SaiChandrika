package com.example.service;



import java.util.List;

import com.example.binding.SsnRequest;
import com.example.binding.SsnResponse;


public interface SsnService {

	public SsnResponse AddSSn(SsnRequest request);
	
	 public Boolean validateSsn(SsnRequest  request);
	 
	// List<SsnResponse> validateAllData(SsnRequest request);
}
