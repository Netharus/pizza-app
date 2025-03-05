package com.modsen.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orderClient")
public interface OrderClient {

    @GetMapping("/users/{userId}")
    ResponseEntity<Boolean> isUserUsed(@PathVariable String userId);
}
