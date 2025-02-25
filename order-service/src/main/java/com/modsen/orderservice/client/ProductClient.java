package com.modsen.orderservice.client;

import com.modsen.orderservice.dto.ProductRequestDto;
import com.modsen.orderservice.dto.ProductResponseForOrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

@Validated
@FeignClient(name = "productClient")
public interface ProductClient {

    @PostMapping("/data")
    ProductResponseForOrderDto getProductData(ProductRequestDto productRequestDto);

}
