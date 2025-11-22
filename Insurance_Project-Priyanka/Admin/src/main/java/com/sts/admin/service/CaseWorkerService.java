package com.sts.admin.service;

import com.sts.admin.binding.CaseWorkerRequest;
import com.sts.admin.binding.CaseWorkerResponse;

import java.util.List;

public interface CaseWorkerService {

    CaseWorkerResponse createCaseWorker(CaseWorkerRequest caseWorkerRequest);

    CaseWorkerResponse getCaseWorker(Long id);

    List<CaseWorkerResponse> getAllCaseWorkers();

   CaseWorkerResponse updateCaseWorker(Long id,CaseWorkerRequest request);
    boolean deleteCaseWorker(Long id);








}
