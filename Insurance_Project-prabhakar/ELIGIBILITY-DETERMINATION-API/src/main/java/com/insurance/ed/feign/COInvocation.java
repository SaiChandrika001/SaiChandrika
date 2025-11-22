package com.insurance.ed.feign;

import com.insurance.ed.binding.CorrespondenceTriggersRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CORRESPONDENCE-API", url = "http://localhost:5056/api/co")
public interface COInvocation {

    @PostMapping(path = "/save")
    ResponseEntity<String> saveTriggers(@RequestBody CorrespondenceTriggersRequest correspondenceTriggersRequest);

}
