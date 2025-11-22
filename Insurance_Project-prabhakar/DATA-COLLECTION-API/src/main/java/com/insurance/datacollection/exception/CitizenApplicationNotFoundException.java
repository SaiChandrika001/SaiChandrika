package com.insurance.datacollection.exception;

public class CitizenApplicationNotFoundException extends DataCollectionException {
    public CitizenApplicationNotFoundException(Integer applicationId) {
        super("Citizen application not found with ID: " + applicationId, "CITIZEN_APP_NOT_FOUND");
    }
}