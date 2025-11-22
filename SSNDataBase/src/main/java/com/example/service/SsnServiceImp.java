package com.example.service;


import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.SsnDataBaseApplication;
import com.example.binding.SsnRequest;
import com.example.binding.SsnResponse;
import com.example.module.SSN;
import com.example.repo.SsnRepo;



@Service
public class SsnServiceImp implements SsnService{

    private final SsnDataBaseApplication ssnDataBaseApplication;

	@Autowired
	public SsnRepo ssnRepo;

    SsnServiceImp(SsnDataBaseApplication ssnDataBaseApplication) {
        this.ssnDataBaseApplication = ssnDataBaseApplication;
    }
	
	@Override
	public SsnResponse AddSSn(SsnRequest request) {
		SSN entity =new SSN();
		BeanUtils.copyProperties(request, entity);
		SSN save = ssnRepo.save(entity);
		if(save==null)
		{
		  System.out.println("Not added");
		}
		SsnResponse response =new SsnResponse();
		BeanUtils.copyProperties(save, response);
		return response;
	}

	@Override
	public Boolean validateSsn(SsnRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public Boolean validatePerson(SsnRequest request) {
//	    Optional<SSN> optionalRecord = ssnRepo.findBySsn(request.getSsn());
//
//	    if (optionalRecord.isPresent()) {
//	        SSN record = optionalRecord.get();
//	        return record.getFullName().equals(request.getFullName()) &&
//	               record.getStateName().equals(request.getStateName());
//	    }
//	    return false;
//	}

	

//	@Override
//	public Boolean validateSsn(SsnRequest request) {
//	   long ssn = request.getSsn();
//	   Optional<SSN> bySsn = ssnRepo.findAll();
//		return bySsn.isPresent();
//	}



}
