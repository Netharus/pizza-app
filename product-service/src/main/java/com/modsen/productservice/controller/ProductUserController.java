package com.modsen.productservice.controller;


import com.modsen.productservice.dto.CategoryResponseDto;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductResponseDto;
import com.modsen.productservice.service.CategoryService;
import com.modsen.productservice.service.ProductService;
import com.modsen.productservice.validator.PageableValid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductUserController {

    private final ProductService productService;

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageContainerDto<ProductResponseDto> findAllProducts(@PageableValid @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                                @RequestParam(defaultValue = "") String keyword) {
        return productService.findAll(pageable, keyword);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public PageContainerDto<CategoryResponseDto> findAllCategories(@PageableValid @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                                   @RequestParam(defaultValue = "") String keyword) {
        return categoryService.findAll(pageable, keyword);
    }

    @GetMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto findCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryResponseDtoById(id);
    }
}
