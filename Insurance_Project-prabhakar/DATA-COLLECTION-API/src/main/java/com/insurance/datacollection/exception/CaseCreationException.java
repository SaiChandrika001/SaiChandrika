package com.insurance.datacollection.exception;

public class CaseCreationException extends DataCollectionException {
    public CaseCreationException(Integer applicationId, String message) {
        super("Failed to create case for application ID " + applicationId + ": " + message, "CASE_CREATION_ERROR");
    }

    public CaseCreationException(Integer applicationId, String message, Throwable cause) {
        super("Failed to create case for application ID " + applicationId + ": " + message, "CASE_CREATION_ERROR", cause);
    }
}