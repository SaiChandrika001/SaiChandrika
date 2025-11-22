package com.sts.admin.service;

import com.sts.admin.Exception.CaseWorkerNotFoundException;
import com.sts.admin.binding.CaseWorkerRequest;
import com.sts.admin.binding.CaseWorkerResponse;
import com.sts.admin.entity.CaseWorker;
import com.sts.admin.repository.CaseWorkerRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CaseWorkerServiceImpl implements CaseWorkerService {

    private final CaseWorkerRepo repo;
    private final SsnEncryptionService encryptionService;

    public CaseWorkerServiceImpl(CaseWorkerRepo repo, SsnEncryptionService encryptionService) {
        this.repo = repo;
        this.encryptionService = encryptionService;
    }

    private void validateCaseWorker(CaseWorkerRequest req) {
        if (req == null) {
            throw new ValidationException("Request cannot be null");
        }
        if (req.getFullName() == null || req.getFullName().trim().isEmpty()) {
            throw new ValidationException("Full Name is required");
        }
        if (req.getEmail() == null || !req.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidationException("Invalid email format");
        }
        if (req.getPhoneNumber() == null || !req.getPhoneNumber().matches("\\d{10}")) {
            throw new ValidationException("Phone Number must be 10 digits");
        }
        if (req.getSsn() == null || !req.getSsn().matches("\\d{9}")) {
            throw new ValidationException("SSN must be exactly 9 digits");
        }
        if (req.getDateOfBirth() != null) {
            // optional: check past date
            Date now = new Date();
            if (!req.getDateOfBirth().before(now)) {
                throw new ValidationException("Date of birth must be in the past");
            }
        }
    }

    @Override
    public CaseWorkerResponse createCaseWorker(CaseWorkerRequest caseWorkerRequest) {
        validateCaseWorker(caseWorkerRequest);

        CaseWorker entity = new CaseWorker();
        // copy only safe/public fields; we will set SSN encrypted & masked explicitly
        BeanUtils.copyProperties(caseWorkerRequest, entity);

        // encrypt SSN and store encrypted string (ensure encryptionService implementations returns non-null)
//        String encrypted = encryptionService.encrypt(caseWorkerRequest.getSsn());
//        entity.setSsnEncrypted(encrypted);

        // NOTE: your entity method is setSsnLast6 but you're storing last 4 digits.
        // Either rename entity to ssnLast4 or change substring to last 6. Here we keep last 4 to match common practice.
        String last4 = caseWorkerRequest.getSsn().substring(caseWorkerRequest.getSsn().length() - 4);
        entity.setSsnLast4(last4); // consider renaming this field to setSsnLast4

        // ssnStatus - use a meaningful enum/string; avoid "Y"
        entity.setSsnStatus("PENDING"); // better than "Y" — indicates verification pending

        repo.save(entity);

        return mapToresponse(entity);
    }

    private CaseWorkerResponse mapToresponse(CaseWorker entity) {
        CaseWorkerResponse response = new CaseWorkerResponse();
        // ignore the encrypted field by property name "ssnEncrypted"
        BeanUtils.copyProperties(entity, response, "ssnEncrypted");

        // build masked SSN (standard format)
        String last = entity.getSsnLast4();
        if (last == null) {
            response.setSsnMasked(null);
        } else {
            response.setSsnMasked("***-**-" + last);
        }

        // use actual status if present, else default to NOT_VERIFIED
        response.setSsnStatus(Objects.requireNonNullElse(entity.getSsnStatus(), "NOT_VERIFIED"));

        return response;
    }

    @Override
    public CaseWorkerResponse getCaseWorker(Long id) {
        CaseWorker worker = repo.findById(id)
                .orElseThrow(() -> new CaseWorkerNotFoundException("CaseWorker Not Found: " + id));
        return mapToresponse(worker);
    }

    @Override
    public List<CaseWorkerResponse> getAllCaseWorkers() {
        List<CaseWorker> all = repo.findAll();
        List<CaseWorkerResponse> responses = new ArrayList<>();
        for (CaseWorker cw : all) {
            responses.add(mapToresponse(cw));
        }
        return responses;
    }

    @Override
    public CaseWorkerResponse updateCaseWorker(Long id, CaseWorkerRequest request) {
        CaseWorker cw = repo.findById(id)
                .orElseThrow(() -> new CaseWorkerNotFoundException("CaseWorker Not Found: " + id));

        // Validate first, then copy onto the entity
        validateCaseWorker(request);

        // copy safe fields (if you want to avoid overwriting some fields, list them explicitly)
        BeanUtils.copyProperties(request, cw, "ssnEncrypted", "ssnLast6", "ssnStatus");

        if (request.getSsn() != null && !request.getSsn().isEmpty()) {
            // validate format (must be 9 digits)
            if (!request.getSsn().matches("\\d{9}")) {
                throw new ValidationException("SSN must be exactly 9 digits");
            }
            String encrypted = encryptionService.encrypt(request.getSsn());
            cw.setSsnEncrypted(encrypted);

            String last4 = request.getSsn().substring(request.getSsn().length() - 4);
            cw.setSsnLast4(last4); // again, consider renaming to setSsnLast4
            // when SSN updated, reset verification status to PENDING
            cw.setSsnStatus("PENDING");
        }

        CaseWorker saved = repo.save(cw);
        return mapToresponse(saved);
    }

    @Override
    public boolean deleteCaseWorker(Long id) {
        if (!repo.existsById(id)) {
            throw new CaseWorkerNotFoundException("Cannot delete. Caseworker not found with id: " + id);
        }
        repo.deleteById(id);
        return true;
    }
}
