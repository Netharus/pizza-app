package com.modsen.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "orderClient")
public interface OrderClient {
}
