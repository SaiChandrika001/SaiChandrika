package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.binding.SsnRequest;
import com.example.binding.SsnResponse;
import com.example.service.SsnService;

@RestController
@RequestMapping("/ssn-api")
public class SsnController {

	@Autowired
	public SsnService ssnService;
	
	@PostMapping("/ssnu")
	public ResponseEntity<SsnResponse> saveSSn(@RequestBody SsnRequest request)
	{
		
		SsnResponse addSSn = ssnService.AddSSn(request);
		
		return new ResponseEntity<SsnResponse>(addSSn, HttpStatus.CREATED);
		
	}
	

    @PostMapping("/valid")
//    public ResponseEntity<Boolean> validatePerson(@RequestBody SsnRequest request) {
//        boolean isValid = ssnService.validatePerson(request);
//        return ResponseEntity.ok(isValid);
//    }
    public Boolean validateSsn(@RequestBody SsnRequest request)
    {
		return ssnService.validateSsn(request);
    }

}
