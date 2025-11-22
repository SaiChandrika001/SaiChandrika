package com.insurance.datacollection.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataCollectionException.class)
    public ResponseEntity<APIError> handleDataCollectionException(DataCollectionException ex, WebRequest request) {
        APIError apiError = new APIError(ex.getMessage(), ex.getErrorCode());
        HttpStatus status = getHttpStatusForException(ex);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(CitizenApplicationNotFoundException.class)
    public ResponseEntity<APIError> handleCitizenApplicationNotFoundException(CitizenApplicationNotFoundException ex, WebRequest request) {
        APIError apiError = new APIError(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CaseNotFoundException.class)
    public ResponseEntity<APIError> handleCaseNotFoundException(CaseNotFoundException ex, WebRequest request) {
        APIError apiError = new APIError(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<APIError> handlePlanNotFoundException(PlanNotFoundException ex, WebRequest request) {
        APIError apiError = new APIError(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<APIError> handleExternalServiceException(ExternalServiceException ex, WebRequest request) {
        APIError apiError = new APIError(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidCaseDataException.class)
    public ResponseEntity<APIError> handleInvalidCaseDataException(InvalidCaseDataException ex, WebRequest request) {
        APIError apiError = new APIError(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<APIError> handleDuplicateDataException(DuplicateDataException ex, WebRequest request) {
        APIError apiError = new APIError(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataSaveException.class)
    public ResponseEntity<APIError> handleDataSaveException(DataSaveException ex, WebRequest request) {
        APIError apiError = new APIError(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CaseCreationException.class)
    public ResponseEntity<APIError> handleCaseCreationException(CaseCreationException ex, WebRequest request) {
        APIError apiError = new APIError(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        APIError apiError = new APIError("Validation failed: " + errors.toString(), "VALIDATION_ERROR");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<APIError> handleDataAccessException(DataAccessException ex, WebRequest request) {
        APIError apiError = new APIError("Database operation failed", "DATABASE_ERROR");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIError> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        APIError apiError = new APIError("Invalid argument provided: " + ex.getMessage(), "INVALID_ARGUMENT");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<APIError> handleNullPointerException(NullPointerException ex, WebRequest request) {
        APIError apiError = new APIError("An unexpected error occurred due to missing data", "NULL_POINTER_ERROR");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleGenericException(Exception ex, WebRequest request) {
        APIError apiError = new APIError("An unexpected error occurred", "INTERNAL_SERVER_ERROR");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpStatus getHttpStatusForException(DataCollectionException ex) {
        String errorCode = ex.getErrorCode();

        switch (errorCode) {
            case "CITIZEN_APP_NOT_FOUND":
            case "CASE_NOT_FOUND":
            case "PLAN_NOT_FOUND":
                return HttpStatus.NOT_FOUND;
            case "INVALID_CASE_DATA":
                return HttpStatus.BAD_REQUEST;
            case "DUPLICATE_DATA":
                return HttpStatus.CONFLICT;
            case "EXTERNAL_SERVICE_ERROR":
                return HttpStatus.SERVICE_UNAVAILABLE;
            case "DATA_SAVE_ERROR":
            case "CASE_CREATION_ERROR":
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}