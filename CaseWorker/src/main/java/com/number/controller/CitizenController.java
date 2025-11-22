package com.number.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.number.binding.CitizenRequest;
import com.number.binding.CitizenResponse;
import com.number.service.CitizenService;

@RestController
@RequestMapping("/citizen")
public class CitizenController {

	@Autowired
	private CitizenService citizenService;
	

	 @PostMapping("/register")
	    public ResponseEntity<CitizenResponse> register(@RequestBody CitizenRequest request) {
	        CitizenResponse response = citizenService.registerCitizen(request);
	        return ResponseEntity.ok(response);
	    }  
	 
	 @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteCitizen(@PathVariable Integer id) {
	        boolean deleted = citizenService.deleteCitizen(id);

	        if (deleted) {
	            return ResponseEntity.ok("Citizen with id "  + id + " deleted successfully.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body("Citizen with id "  + id + " not found.");
	        }
	    }
	 @GetMapping("/all")
	 public ResponseEntity<List<CitizenResponse>> List()
	 {
		return ResponseEntity.ok(citizenService.getAllCitizens());
		 
	 }
	 
	  @PutMapping("/{id}")
	    public ResponseEntity<List<CitizenResponse>> updateCitizen(
	            @PathVariable Integer id,
	            @RequestBody CitizenRequest request) {

	        List<CitizenResponse> responses = citizenService.updateCitizen(request, id);

	        // If update failed (status == FAIL)
	        if (!responses.isEmpty() && "FAIL".equalsIgnoreCase(responses.get(0).getStatus())) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responses);
	        }

	        return ResponseEntity.ok(responses);
	    }
	 
}
