package com.insurance.datacollection.exception;

public class CaseNotFoundException extends DataCollectionException {
    public CaseNotFoundException(Long caseNumber) {
        super("Case not found with number: " + caseNumber, "CASE_NOT_FOUND");
    }
}