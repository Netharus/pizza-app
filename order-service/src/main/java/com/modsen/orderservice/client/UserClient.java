package com.modsen.orderservice.client;

import com.modsen.orderservice.dto.UsersResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userClient")
public interface UserClient {

    @GetMapping("/{id}")
    UsersResponseDto findById(@PathVariable Long id);
}
