package com.insurance.datacollection.dao.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DC_EDUCATION")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDUCATION_ID")
    private Integer educationId;

    @Column(name = "CASE_NUMBER")
    private Long caseNumber;

    @Column(name = "HIGHEST_QUALIFICATION")
    private String highestQualification;

    @Column(name = "GRADUATION_YEAR")
    private Integer graduationYear;

    @Column(name = "UNIVERSITY")
    private String university;

    @CreationTimestamp
    @Column(name = "CREATED_ON", updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "UPDATED_ON", insertable = false)
    private LocalDateTime updatedOn;
}
