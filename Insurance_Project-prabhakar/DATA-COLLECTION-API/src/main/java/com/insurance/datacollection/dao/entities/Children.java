package com.insurance.datacollection.dao.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DC_CHILDREN")
public class Children {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHILDREN_ID")
    private Integer childrenId;

    @Column(name = "CASE_NUMBER")
    private Long caseNumber;

    @Column(name = "CHILDREN_NAME")
    private String childrenName;

    @Column(name = "CHILDREN_AGE")
    private Integer childrenAge;

    @Column(name = "CHILDREN_SSN")
    private Long childrenSSN;

    @CreationTimestamp
    @Column(name = "CREATED_ON", updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "UPDATED_ON", insertable = false)
    private LocalDateTime updatedOn;
}
