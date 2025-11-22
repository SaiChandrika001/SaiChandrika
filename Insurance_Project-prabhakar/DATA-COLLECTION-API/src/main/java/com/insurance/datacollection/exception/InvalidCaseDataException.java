package com.insurance.datacollection.exception;

public class InvalidCaseDataException extends DataCollectionException {
    public InvalidCaseDataException(String message) {
        super("Invalid case data: " + message, "INVALID_CASE_DATA");
    }
}