package com.modsen.productservice.service;

import com.modsen.productservice.domain.Category;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductCreateDto;
import com.modsen.productservice.dto.ProductForCategoryResponseDto;
import com.modsen.productservice.dto.ProductResponseDto;
import com.modsen.productservice.dto.ProductStandaloneCreateDto;
import com.modsen.productservice.dto.ProductUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponseDto findById(Long id);

    List<ProductResponseDto> findAll();

    PageContainerDto<ProductResponseDto> findAll(Pageable pageable, String keyword);

    ProductResponseDto updateProduct(ProductUpdateDto productUpdateDto, Category category);

    ProductForCategoryResponseDto createProduct(ProductCreateDto productCreateDto, Category category);

    ProductResponseDto createProduct(ProductStandaloneCreateDto productCreateDto, Category category);

    void deleteProduct(Long id);

}
