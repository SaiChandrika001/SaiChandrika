package com.insurance.datacollection.exception;

public class PlanNotFoundException extends DataCollectionException {
    public PlanNotFoundException(Integer planId) {
        super("Plan not found with ID: " + planId, "PLAN_NOT_FOUND");
    }
}