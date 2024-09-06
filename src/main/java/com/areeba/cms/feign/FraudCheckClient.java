package com.areeba.cms.feign;

import com.areeba.cms.dto.RequestFraudCheckDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "fraud-check-service", url = "http://localhost:8081/api")
public interface FraudCheckClient {

    @PostMapping("/fraud-check")
    Boolean checkFraud(@RequestBody RequestFraudCheckDto dto);
}
