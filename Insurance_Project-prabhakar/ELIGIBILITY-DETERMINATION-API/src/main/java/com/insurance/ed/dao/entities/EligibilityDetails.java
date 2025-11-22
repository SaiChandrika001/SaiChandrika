package com.insurance.ed.dao.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ELIGIBILITY_DETAILS")
public class EligibilityDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ELIGIBILITY_ID")
    private Integer eligibilityId;

    @Column(name = "CASE_NUMBER", unique = true)
    private Long caseNumber;

    @Column(name = "HOLDER_NAME")
    private String holderName;

    @Column(name = "HOLDER_SSN")
    private String holderSSN;

    @Column(name = "PLAN_NAME")
    private String planName;

    @Column(name = "PLAN_STATUS")
    private String planStatus;

    @Column(name = "PLAN_START_DATE")
    private LocalDate planStartDate;

    @Column(name = "PLAN_END_DATE")
    private LocalDate planEndDate;

    @Column(name = "BENEFIT_AMOUNT")
    private Double benefitAmount;

    @Column(name = "DENIAL_REASON")
    private String denialReason;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "CREATED_ON", updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "UPDATED_ON", insertable = false)
    private LocalDateTime updatedOn;
}
