package com.insurance.ed.dao.repositories;

import com.insurance.ed.dao.entities.EligibilityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EligibilityDetailsRepository extends JpaRepository<EligibilityDetails, Integer> {

    EligibilityDetails findByCaseNumber(Long caseNumber);

    // For REPORTS-API
    @Query("SELECT DISTINCT e.planName FROM EligibilityDetails e WHERE e.planName IS NOT NULL")
    List<String> findDistinctPlanNames();

    @Query("SELECT DISTINCT e.planStatus FROM EligibilityDetails e WHERE e.planStatus IS NOT NULL")
    List<String> findDistinctPlanStatuses();

    List<EligibilityDetails> findByHolderSSN(String holderSSN);

    // Advanced search query
    @Query("SELECT e FROM EligibilityDetails e WHERE " +
            "(:planName IS NULL OR e.planName = :planName) AND " +
            "(:planStatus IS NULL OR e.planStatus = :planStatus) AND " +
            "(:planStartDate IS NULL OR e.planStartDate >= :planStartDate) AND " +
            "(:planEndDate IS NULL OR e.planEndDate <= :planEndDate)")
    List<EligibilityDetails> searchByFilters(
            @Param("planName") String planName,
            @Param("planStatus") String planStatus,
            @Param("planStartDate") LocalDate planStartDate,
            @Param("planEndDate") LocalDate planEndDate
    );

    // Additional useful queries
    @Query("SELECT e FROM EligibilityDetails e WHERE e.planStatus = 'APPROVED'")
    List<EligibilityDetails> findAllApprovedBenefits();

    @Query("SELECT e FROM EligibilityDetails e WHERE e.planStatus = 'DENIED'")
    List<EligibilityDetails> findAllDeniedBenefits();

    @Query("SELECT e FROM EligibilityDetails e WHERE e.planStartDate BETWEEN :startDate AND :endDate")
    List<EligibilityDetails> findByPlanStartDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}