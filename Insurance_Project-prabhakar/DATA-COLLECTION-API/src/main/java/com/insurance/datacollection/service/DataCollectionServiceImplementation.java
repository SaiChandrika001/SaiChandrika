package com.insurance.datacollection.service;

import com.insurance.datacollection.binding.*;
import com.insurance.datacollection.dao.entities.Children;
import com.insurance.datacollection.dao.entities.DCCase;
import com.insurance.datacollection.dao.entities.Education;
import com.insurance.datacollection.dao.entities.Income;
import com.insurance.datacollection.dao.repositories.ChildrenRepository;
import com.insurance.datacollection.dao.repositories.DCCaseRepository;
import com.insurance.datacollection.dao.repositories.EducationRepository;
import com.insurance.datacollection.dao.repositories.IncomeRepository;
import com.insurance.datacollection.exception.*;
import com.insurance.datacollection.feign.ARInvocation;
import com.insurance.datacollection.feign.PlansInvocation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataCollectionServiceImplementation implements DataCollectionService {

    private final PlansInvocation plansInvocation;
    private final ARInvocation arInvocation;
    private final ChildrenRepository childrenRepository;
    private final DCCaseRepository dcCaseRepository;
    private final EducationRepository educationRepository;
    private final IncomeRepository incomeRepository;

    @Override
    public Long loadCaseNumber(Integer applicationId) {
        if (applicationId == null) {
            throw new IllegalArgumentException("Application ID cannot be null");
        }

        try {
            ResponseEntity<String> stringResponseEntity = arInvocation.verifyCitizenByApplicationId(applicationId);
            if (!stringResponseEntity.getStatusCode().is2xxSuccessful()) {
                throw new CitizenApplicationNotFoundException(applicationId);
            }

            DCCase entity = new DCCase();
            entity.setApplicationId(applicationId);
            entity = dcCaseRepository.save(entity);

            return entity.getCaseNumber();

        } catch (DataAccessException ex) {
            throw new CaseCreationException(applicationId, "Server error occurred", ex);
        } catch (Exception ex) {
            throw new CaseCreationException(applicationId, "Unexpected error occurred", ex);
        }
    }

    @Override
    public Map<Integer, String> getPlans() {
        try {
            ResponseEntity<Map<Integer, String>> response = plansInvocation.getAllPlans();

            if (response == null || response.getBody() == null) {
                throw new ExternalServiceException("Plans Service", "No response received from plans service");
            }

            return response.getBody();

        } catch (Exception ex) {
            throw new ExternalServiceException("Plans Service", "Failed to fetch plans", ex);
        }
    }

    @Override
    public Long savePlanSelection(PlanSelectionRequest planSelectionRequest) {
        if (planSelectionRequest == null) {
            throw new IllegalArgumentException("Plan selection request cannot be null");
        }

        if (planSelectionRequest.getCaseNumber() == null) {
            throw new InvalidCaseDataException("Case number is required");
        }

        if (planSelectionRequest.getPlanId() == null) {
            throw new InvalidCaseDataException("Plan ID is required");
        }

        try {
            Optional<DCCase> findById = dcCaseRepository.findById(planSelectionRequest.getCaseNumber());

            if (findById.isEmpty()) {
                throw new CaseNotFoundException(planSelectionRequest.getCaseNumber());
            }

            Map<Integer, String> plans = getPlans();
            if (!plans.containsKey(planSelectionRequest.getPlanId())) {
                throw new PlanNotFoundException(planSelectionRequest.getPlanId());
            }

            DCCase entity = findById.get();
            entity.setPlanId(planSelectionRequest.getPlanId());
            dcCaseRepository.save(entity);

            return planSelectionRequest.getCaseNumber();

        } catch (DataCollectionException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new DataSaveException("plan selection", "Database error occurred", ex);
        } catch (Exception ex) {
            throw new DataSaveException("plan selection", "Unexpected error occurred", ex);
        }
    }

    @Override
    public Long saveIncomeData(IncomeRequest incomeRequest) {
        if (incomeRequest == null) {
            throw new IllegalArgumentException("Income request cannot be null");
        }

        if (incomeRequest.getCaseNumber() == null) {
            throw new InvalidCaseDataException("Case number is required for income data");
        }

        try {
            validateCaseExists(incomeRequest.getCaseNumber());

            Income income = new Income();
            BeanUtils.copyProperties(incomeRequest, income);
            incomeRepository.save(income);

            return incomeRequest.getCaseNumber();

        } catch (DataCollectionException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new DataSaveException("income", "Database error occurred", ex);
        } catch (Exception ex) {
            throw new DataSaveException("income", "Unexpected error occurred", ex);
        }
    }

    @Override
    public Long saveEducation(EducationRequest educationRequest) {
        if (educationRequest == null) {
            throw new IllegalArgumentException("Education request cannot be null");
        }

        if (educationRequest.getCaseNumber() == null) {
            throw new InvalidCaseDataException("Case number is required for education data");
        }

        try {
            validateCaseExists(educationRequest.getCaseNumber());

            Education education = new Education();
            BeanUtils.copyProperties(educationRequest, education);
            educationRepository.save(education);

            return educationRequest.getCaseNumber();

        } catch (DataCollectionException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new DataSaveException("education", "Database error occurred", ex);
        } catch (Exception ex) {
            throw new DataSaveException("education", "Unexpected error occurred", ex);
        }
    }

    @Override
    public Long saveChildrenData(ChildRequest childrenRequest) {
        if (childrenRequest == null) {
            throw new IllegalArgumentException("Children request cannot be null");
        }

        if (childrenRequest.getCaseNumber() == null) {
            throw new InvalidCaseDataException("Case number is required for children data");
        }

        try {
            validateCaseExists(childrenRequest.getCaseNumber());

            Children children = new Children();
            children.setCaseNumber(childrenRequest.getCaseNumber());
            children.setChildrenAge(childrenRequest.getChildrenAge());
            children.setChildrenName(childrenRequest.getChildrenName());
            children.setChildrenSSN(childrenRequest.getChildrenSSN());

            childrenRepository.save(children);

            return childrenRequest.getCaseNumber();

        } catch (DataCollectionException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new DataSaveException("children", "Database error occurred", ex);
        } catch (Exception ex) {
            throw new DataSaveException("children", "Unexpected error occurred", ex);
        }
    }

    @Override
    public Summary getSummary(Long caseNumber) {
        if (caseNumber == null) {
            throw new IllegalArgumentException("Case number cannot be null");
        }

        try {
            validateCaseExists(caseNumber);

            Summary summary = new Summary();

            List<Income> incomes = incomeRepository.findAll();
            for (Income income : incomes) {
                if (income.getCaseNumber().equals(caseNumber)) {
                    IncomeRequest incomeRequest = new IncomeRequest();
                    BeanUtils.copyProperties(income, incomeRequest);
                    summary.setIncomeRequest(incomeRequest);
                    break;
                }
            }

            List<Education> educations = educationRepository.findAll();
            for (Education education : educations) {
                if (education.getCaseNumber().equals(caseNumber)) {
                    EducationRequest educationRequest = new EducationRequest();
                    BeanUtils.copyProperties(education, educationRequest);
                    summary.setEducationRequest(educationRequest);
                    break;
                }
            }

            List<Children> childrenByCaseNumber = childrenRepository.getChildrenByCaseNumber(caseNumber);
            List<ChildRequest> childRequestList = new ArrayList<>();
            for (Children children : childrenByCaseNumber) {
                ChildRequest childRequest = new ChildRequest();
                childRequest.setCaseNumber(children.getCaseNumber());
                childRequest.setChildrenAge(children.getChildrenAge());
                childRequest.setChildrenName(children.getChildrenName());
                childRequest.setChildrenSSN(children.getChildrenSSN());
                childRequestList.add(childRequest);
            }

            summary.setChildrenRequest(childRequestList);
            DCCase byCaseNumber = dcCaseRepository.findByCaseNumber(caseNumber);
            summary.setApplicationId(byCaseNumber.getApplicationId());

            Optional<DCCase> dcCase = dcCaseRepository.findById(caseNumber);
            if (dcCase.isPresent() && dcCase.get().getPlanId() != null) {
                Map<Integer, String> plans = getPlans();
                String planName = plans.get(dcCase.get().getPlanId());
                summary.setPlanName(planName);
            }

            return summary;

        } catch (DataCollectionException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new DataSaveException("summary", "Database error occurred", ex);
        } catch (Exception ex) {
            throw new DataSaveException("summary", "Unexpected error occurred", ex);
        }
    }

    @Override
    public DCCaseResponse findByCaseNumber(Long caseNumber) {
        DCCase byCaseNumber = dcCaseRepository.findByCaseNumber(caseNumber);
        if (byCaseNumber == null) {
            throw new InvalidCaseDataException("Case number not found");
        }
        DCCaseResponse dcCaseResponse = new DCCaseResponse();
        dcCaseResponse.setCaseNumber(byCaseNumber.getCaseNumber());
        dcCaseResponse.setApplicationId(byCaseNumber.getApplicationId());
        dcCaseResponse.setPlanId(byCaseNumber.getPlanId());
        return dcCaseResponse;
    }


    private void validateCaseExists(Long caseNumber) {
        if (!dcCaseRepository.existsById(caseNumber)) {
            throw new CaseNotFoundException(caseNumber);
        }
    }
}