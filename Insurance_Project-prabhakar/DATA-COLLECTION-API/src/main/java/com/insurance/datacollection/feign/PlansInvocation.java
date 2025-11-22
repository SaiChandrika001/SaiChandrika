package com.insurance.datacollection.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "ADMIN-API", url = "http://localhost:5001/api/plans")
public interface PlansInvocation {

    @GetMapping
    ResponseEntity<Map<Integer, String>> getAllPlans();
}
