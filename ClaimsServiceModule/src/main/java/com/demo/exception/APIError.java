package com.demo.exception;

import lombok.Data;

@Data
public class APIError {
	
	private String error;
	private String message;
}
