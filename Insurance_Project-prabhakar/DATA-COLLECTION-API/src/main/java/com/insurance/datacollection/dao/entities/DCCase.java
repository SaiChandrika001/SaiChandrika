package com.insurance.datacollection.dao.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DC_CASE")
public class DCCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CASE_NUMBER")
    private Long caseNumber;

    @Column(name = "APPLICATION_ID")
    private Integer applicationId;

    @Column(name = "PLAN_ID")
    private Integer planId;

    @CreationTimestamp
    @Column(name = "CREATED_ON", updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "UPDATED_ON", insertable = false)
    private LocalDateTime updatedOn;
}
