package com.insurance.datacollection.exception;

import lombok.Getter;

@Getter
public class DataCollectionException extends RuntimeException {

    private final String errorCode;

    public DataCollectionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DataCollectionException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}