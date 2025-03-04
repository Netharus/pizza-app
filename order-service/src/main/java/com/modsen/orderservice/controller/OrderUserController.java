package com.modsen.orderservice.controller;

import com.modsen.orderservice.dto.OrderResponseDto;
import com.modsen.orderservice.dto.PageContainerDto;
import com.modsen.orderservice.service.OrderService;
import com.modsen.orderservice.validator.PageableValid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderUserController {

    private final OrderService orderService;

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public PageContainerDto<OrderResponseDto> getOrders(@PageableValid @PageableDefault(sort = "id") Pageable pageable,
                                                        @RequestParam(defaultValue = "") String keyword,
                                                        @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return orderService.findAll(pageable, keyword, userId);
    }

    @GetMapping("/user/actual")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getOrdersActual(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return orderService.getActualOrders(userId);
    }
}
