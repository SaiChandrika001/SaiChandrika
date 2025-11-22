package com.insurance.datacollection.binding;

import lombok.Data;

@Data
public class EducationRequest {

    private Long caseNumber;

    private String highestQualification;

    private Integer graduationYear;

    private String university;
}
