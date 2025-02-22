package com.modsen.productservice.service;

import com.modsen.productservice.domain.Category;
import com.modsen.productservice.dto.CategoryCreateDto;
import com.modsen.productservice.dto.CategoryResponseDto;
import com.modsen.productservice.dto.CategoryUpdateDto;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductForCategoryResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto getCategoryResponseDtoById(Long id);

    Category getCategoryById(Long id);

    List<CategoryResponseDto> findAll();

    PageContainerDto<CategoryResponseDto> findAll(Pageable pageable, String keyword);

    CategoryResponseDto updateCategory(CategoryUpdateDto categoryUpdateDto);

    Category createCategory(CategoryCreateDto categoryCreateDto);

    void deleteCategory(Long id);

    CategoryResponseDto getCategoryResponseDto(Category category, List<ProductForCategoryResponseDto> products);
}
