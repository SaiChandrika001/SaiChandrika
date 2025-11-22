package com.sts.admin.Exception;

import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.DateTimeException;

public class GlobalExceptionHandler {


    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<APIError> handleCategoryNotFoundException(CategoryNotFoundException e) {

        APIError error = new APIError(e.getMessage(), "EX101");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<APIError> handleDateException(DateException e) {
        APIError error = new APIError(e.getMessage(), "EX102");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(UnableToSaveException.class)
    public ResponseEntity<APIError> handleUnableToSaveException(UnableToSaveException e) {
        APIError error = new APIError(e.getMessage(), "EX103");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<APIError> handlePlanNotFoundException(PlanNotFoundException e) {
        APIError error = new APIError(e.getMessage(), "EX104");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(StatusException.class)
    public ResponseEntity<APIError> handleStatusException(StatusException e) {
        APIError error = new APIError(e.getMessage(), "EX105");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnableToUpdateException.class)
    public ResponseEntity<APIError> handleUnableToUpdateException(UnableToUpdateException e) {
        APIError error = new APIError(e.getMessage(), "EX106");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnableToDeleteException.class)
    public ResponseEntity<APIError> handleUnableToDeleteException(UnableToDeleteException e) {
        APIError error = new APIError(e.getMessage(), "EX107");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(CaseWorkerNotFoundException.class)
    public ResponseEntity<APIError> handleCaseWorkerNotFoundException(CaseWorkerNotFoundException e) {
        APIError error = new APIError(e.getMessage(), "EX107");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

}