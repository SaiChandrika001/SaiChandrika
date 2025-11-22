package com.support.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIError> handleNotFound(UserNotFoundException ex){
        APIError error = new APIError();
        error.setMessage("Not Found");
        error.setCode("101");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
