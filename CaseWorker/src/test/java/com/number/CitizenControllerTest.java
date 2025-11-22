package com.number;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.number.binding.CitizenRequest;
import com.number.binding.CitizenResponse;
import com.number.controller.CitizenController;
import com.number.service.CitizenService;
@WebMvcTest(CitizenController.class)
public class CitizenControllerTest {

	 @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @MockBean
	    private CitizenService citizenService;
	    
	    @Autowired
	    private CitizenController citizenController;
	    
	    private CitizenRequest request;


	    @Test
	    void testRegisterCitizen_Success() throws Exception {
	        // Arrange
	        CitizenRequest request = new CitizenRequest();
	        request.setSsn(9848270443L);
	        request.setFullName("John");
	        request.setEmail("john@example.com");
	        request.setPhno(9876543210L);
	        request.setGender("Male");
	        request.setDob(LocalDate.of(1990, 5, 15));
	        request.setStateName("Texas");

	        CitizenResponse response = new CitizenResponse();
	        response.setStatus("SUCCESS");
	        response.setMessage("Citizen registered successfully");

	        when(citizenService.registerCitizen(request)).thenReturn(response);

	        // Act & Assert
	        mockMvc.perform(post("/citizen/register")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(request)))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.status", is("SUCCESS")))
	                .andExpect(jsonPath("$.message", is("Citizen registered successfully")));
	    }

	    @Test
	    void testRegisterCitizen_Fail() throws Exception {
	        // Arrange
	        CitizenRequest request = new CitizenRequest();
	        request.setSsn(9848270443L);

	        CitizenResponse response = new CitizenResponse();
	        response.setStatus("FAIL");
	        response.setMessage("Citizen already registered with this SSN");

	        when(citizenService.registerCitizen(request)).thenReturn(response);

	        // Act & Assert
	        mockMvc.perform(post("/citizen/register")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(request)))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.status", is("FAIL")))
	                .andExpect(jsonPath("$.message", is("Citizen already registered with this SSN")));
	    }
	    
	    
	    @Test
	    void testListAllCitizens() {
	        // Arrange
	        List<CitizenResponse> mockList = List.of(new CitizenResponse(), new CitizenResponse());
	        when(citizenService.getAllCitizens()).thenReturn(mockList);

	        // Act
	        ResponseEntity<List<CitizenResponse>> result = citizenController.List();

	        // Assert
	        assertEquals(200, result.getStatusCodeValue());
	        assertEquals(2, result.getBody().size());
	        verify(citizenService).getAllCitizens();
	    
	    
	}
	    
	    @Test
	    void testDeleteCitizen_Success() {
	        when(citizenService.deleteCitizen(1)).thenReturn(true);

	        ResponseEntity<String> result = citizenController.deleteCitizen(1);

	        assertEquals(200, result.getStatusCodeValue());
	        assertTrue(result.getBody().contains("deleted successfully"));
	        verify(citizenService, times(1)).deleteCitizen(1);
	    }

	    @Test
	    void testDeleteCitizen_NotFound() {
	        when(citizenService.deleteCitizen(99)).thenReturn(false);

	        ResponseEntity<String> result = citizenController.deleteCitizen(99);

	        assertEquals(404, result.getStatusCodeValue());
	        assertTrue(result.getBody().contains("not found"));
	    }

	    @Test
	    void testUpdateCitizen_Success() {
	        CitizenResponse updated = new CitizenResponse();
	        updated.setStatus("SUCCESS");
	        List<CitizenResponse> mockList = List.of(updated);

	        when(citizenService.updateCitizen(request, 1)).thenReturn(mockList);

	        ResponseEntity<List<CitizenResponse>> result = citizenController.updateCitizen(1, request);

	        assertEquals(200, result.getStatusCodeValue());
	        assertEquals("SUCCESS", result.getBody().get(0).getStatus());
	    }

	    @Test
	    void testUpdateCitizen_Fail() {
	        CitizenResponse failResponse = new CitizenResponse();
	        failResponse.setStatus("FAIL");
	        List<CitizenResponse> failList = List.of(failResponse);

	        when(citizenService.updateCitizen(request, 10)).thenReturn(failList);

	        ResponseEntity<List<CitizenResponse>> result = citizenController.updateCitizen(10, request);

	        assertEquals(404, result.getStatusCodeValue());
	        assertEquals("FAIL", result.getBody().get(0).getStatus());
	    }
}

 