package com.modsen.productservice.controller;

import com.modsen.productservice.dto.CategoryResponseDto;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductResponseDto;
import com.modsen.productservice.dto.ProductStandaloneCreateDto;
import com.modsen.productservice.dto.ProductUpdateDto;
import com.modsen.productservice.service.CategoryService;
import com.modsen.productservice.service.ProductService;
import com.modsen.productservice.validator.PageableValid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageContainerDto<ProductResponseDto> findAllProducts(@PageableValid @PageableDefault(sort = "ID", direction = Sort.Direction.DESC) Pageable pageable,
                                                                @RequestParam(defaultValue = "") String keyword) {
        return productService.findAll(pageable, keyword);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@Valid @RequestBody ProductStandaloneCreateDto productStandaloneCreateDto) {
        return productService.createProduct(productStandaloneCreateDto, categoryService.getCategoryById(productStandaloneCreateDto.categoryId()));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto) {
        return productService.updateProduct(productUpdateDto,productUpdateDto.categoryId()==null?null:categoryService.getCategoryById(productUpdateDto.categoryId()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


}
