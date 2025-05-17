package com.modsen.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orderClient")
@Validated
public interface OrderClient {

    @GetMapping("/product/{id}")
    ResponseEntity<Boolean> isProductUsed(@PathVariable Long id);
}
