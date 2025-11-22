package com.insurance.ed.feign;

import com.insurance.ed.binding.Summary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DATA-COLLECTION-API", url = "http://localhost:5054/api/summary")
public interface DCInvocation {

    @GetMapping("/{caseNumber}")
    ResponseEntity<Summary> getSummary(@PathVariable Long caseNumber);

}
