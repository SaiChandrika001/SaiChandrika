package com.insurance.datacollection.dao.repositories;

import com.insurance.datacollection.dao.entities.Children;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildrenRepository extends JpaRepository<Children, Integer> {
    List<Children> getChildrenByCaseNumber(Long caseNumber);
}
