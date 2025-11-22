package com.applicationregistration.modal;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DETAILS")
@Data
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELIGIBILITY_ID")
    private Integer eligibilityId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MOBILE_NUMBER")
    private Long mobileNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "GENDER")
    private Character gender;

    @Column(name = "SSN")
    private Long ssn;

    @Column(name = "PLAN_NAME")
    private String planName;

    @Column(name = "PLAN_STATUS")
    private String planStatus;

    @Column(name = "PLAN_START_DATE")
    private LocalDate planStartDate;

    @Column(name = "PLAN_END_DATE")
    private LocalDate planEndDate;

    @Column(name = "CREATE_DATE")
    private LocalDate createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
