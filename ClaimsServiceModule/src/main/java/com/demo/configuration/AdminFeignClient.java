package com.demo.configuration;

import java.util.Map;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ADMIN")   // service name registered in Eureka
public interface AdminFeignClient {

    @GetMapping("/admin/api/caseworker/{id}")
    CaseWorkerResponse getCaseWorker(@PathVariable Long id);
}
