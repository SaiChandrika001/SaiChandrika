package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIError> handleNotFound(ResourceNotFoundException e) {
		APIError api = new APIError();
		api.setError("EXP125");
		api.setMessage(e.getMessage());
		return new ResponseEntity<APIError>(api,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnableToCreateClaimException.class)
	public ResponseEntity<APIError> handleNotCreate(UnableToCreateClaimException e) {
		APIError api = new APIError();
		api.setError("EXP358");
		api.setMessage(e.getMessage());
		return new ResponseEntity<APIError>(api,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnableToUpdateClaimException.class)
	public ResponseEntity<APIError> handleNotUpdate(ResourceNotFoundException e) {
		APIError api = new APIError();
		api.setError("EXP235");
		api.setMessage(e.getMessage());
		return new ResponseEntity<APIError>(api,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnableToFetchClaimsException.class)
	public ResponseEntity<APIError> handleNotFetch(UnableToFetchClaimsException e) {
		APIError api = new APIError();
		api.setError("EXP107");
		api.setMessage(e.getMessage());
		return new ResponseEntity<APIError>(api,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnableToDeleteClaimException.class)
	public ResponseEntity<APIError> handleNotDelete(UnableToDeleteClaimException e) {
		APIError api = new APIError();
		api.setError("EXP197");
		api.setMessage(e.getMessage());
		return new ResponseEntity<APIError>(api,HttpStatus.NOT_FOUND);
	}
	
	

}
