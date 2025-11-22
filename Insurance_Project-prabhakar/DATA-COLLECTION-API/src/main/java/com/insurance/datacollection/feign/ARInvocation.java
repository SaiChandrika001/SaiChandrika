package com.insurance.datacollection.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "APPLICATION-REGISTRATION-API", url = "http://localhost:5053/api/ar")
public interface ARInvocation {

    @GetMapping(path = "/citizen/{applicationId}")
    ResponseEntity<String> verifyCitizenByApplicationId(@PathVariable Integer applicationId);
}
