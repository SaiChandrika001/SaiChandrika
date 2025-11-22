package com.sts.admin.repository;

import com.sts.admin.entity.CaseWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaseWorkerRepo extends JpaRepository<CaseWorker,Long> {
    Optional<CaseWorker> findByEmail(String  email);
}
