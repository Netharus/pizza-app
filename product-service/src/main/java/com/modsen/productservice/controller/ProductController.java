package com.modsen.productservice.controller;

import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductResponseDto;
import com.modsen.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageContainerDto<ProductResponseDto> findAllProducts(@PageableDefault(page = 0, size = 10, sort = "ID", direction = Sort.Direction.DESC) Pageable pageable,
                                                                @RequestParam(defaultValue = "") String keyword) {
        return productService.findAll(pageable, keyword);
    }
}
