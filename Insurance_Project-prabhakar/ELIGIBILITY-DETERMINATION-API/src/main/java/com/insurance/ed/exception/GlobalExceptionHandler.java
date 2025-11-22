package com.insurance.ed.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<APIError> handleDataNotFoundException(DataNotFoundException ex) {
        APIError error = new APIError(ex.getMessage(), "DATA_NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<APIError> handleServiceUnavailableException(ServiceUnavailableException ex) {
        APIError error = new APIError(ex.getMessage(), "SERVICE_UNAVAILABLE");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<APIError> handleInvalidDataException(InvalidDataException ex) {
        APIError error = new APIError(ex.getMessage(), "INVALID_DATA");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EligibilityProcessingException.class)
    public ResponseEntity<APIError> handleEligibilityProcessingException(EligibilityProcessingException ex) {
        APIError error = new APIError(ex.getMessage(), "ELIGIBILITY_PROCESSING_FAILED");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<APIError> handleFeignException(FeignException ex) {
        APIError error = new APIError("Unable to communicate with external service", "EXTERNAL_SERVICE_ERROR");
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIError> handleIllegalArgumentException(IllegalArgumentException ex) {
        APIError error = new APIError(ex.getMessage(), "INVALID_ARGUMENT");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleGenericException(Exception ex) {
        APIError error = new APIError("An unexpected error occurred", "INTERNAL_SERVER_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
