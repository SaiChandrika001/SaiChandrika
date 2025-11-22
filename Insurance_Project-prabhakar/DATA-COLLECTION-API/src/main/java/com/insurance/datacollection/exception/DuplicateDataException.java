package com.insurance.datacollection.exception;

public class DuplicateDataException extends DataCollectionException {
    public DuplicateDataException(String dataType, String identifier) {
        super("Duplicate " + dataType + " data found for identifier: " + identifier, "DUPLICATE_DATA");
    }
}