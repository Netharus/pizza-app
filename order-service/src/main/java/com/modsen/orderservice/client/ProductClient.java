package com.modsen.orderservice.client;

import com.modsen.orderservice.dto.PriceRequestDto;
import com.modsen.orderservice.dto.PriceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "productClient")
public interface ProductClient {

    @PostMapping("/actual_prices")
    @ResponseStatus(HttpStatus.OK)
    PriceResponseDto getActualPrices(PriceRequestDto priceRequestDto);

}
