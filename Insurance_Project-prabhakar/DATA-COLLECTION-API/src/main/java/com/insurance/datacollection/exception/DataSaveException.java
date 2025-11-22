package com.insurance.datacollection.exception;

public class DataSaveException extends DataCollectionException {
    public DataSaveException(String dataType, String message) {
        super("Failed to save " + dataType + " data: " + message, "DATA_SAVE_ERROR");
    }

    public DataSaveException(String dataType, String message, Throwable cause) {
        super("Failed to save " + dataType + " data: " + message, "DATA_SAVE_ERROR", cause);
    }
}