package com.insurance.datacollection.dao.repositories;

import com.insurance.datacollection.dao.entities.DCCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DCCaseRepository extends JpaRepository<DCCase, Long> {

    DCCase findByCaseNumber(Long caseNumber);
}
