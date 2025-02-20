package com.modsen.productservice.service;

import com.modsen.productservice.dto.CategoryCreateDto;
import com.modsen.productservice.dto.CategoryResponseDto;
import com.modsen.productservice.dto.CategoryUpdateDto;
import com.modsen.productservice.dto.PageContainerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto findById(Long id);

    List<CategoryResponseDto> findAll();

    PageContainerDto<CategoryResponseDto> findAll(Pageable pageable, String keyword);

    CategoryResponseDto updateCategory(CategoryUpdateDto categoryUpdateDto);

    CategoryResponseDto createCategory(CategoryCreateDto categoryCreateDto);

    void deleteCategory(Long id);
}
