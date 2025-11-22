package com.insurance.datacollection.dao.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DC_INCOME")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incomeId;

    @Column(name = "CASE_NUMBER")
    private Long caseNumber;

    @Column(name = "SALARY_INCOME")
    private Double salaryIncome;

    @Column(name = "PROPERTY_INCOME")
    private Double propertyIncome;

    @Column(name = "RENT_INCOME")
    private Double rentIncome;

    @CreationTimestamp
    @Column(name = "CREATED_ON", updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "UPDATED_ON", insertable = false)
    private LocalDateTime updatedOn;
}
