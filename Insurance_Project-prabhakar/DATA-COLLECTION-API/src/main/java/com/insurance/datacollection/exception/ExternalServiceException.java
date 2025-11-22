package com.insurance.datacollection.exception;

public class ExternalServiceException extends DataCollectionException {
    public ExternalServiceException(String serviceName, String message) {
        super("Error communicating with " + serviceName + ": " + message, "EXTERNAL_SERVICE_ERROR");
    }

    public ExternalServiceException(String serviceName, String message, Throwable cause) {
        super("Error communicating with " + serviceName + ": " + message, "EXTERNAL_SERVICE_ERROR", cause);
    }
}