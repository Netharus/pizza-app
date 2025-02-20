package com.modsen.productservice.service.impl;

import com.modsen.productservice.dto.CategoryCreateDto;
import com.modsen.productservice.dto.CategoryResponseDto;
import com.modsen.productservice.dto.CategoryUpdateDto;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryResponseDto findById(Long id) {
        return null;
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        return List.of();
    }

    @Override
    public PageContainerDto<CategoryResponseDto> findAll(Pageable pageable, String keyword) {
        return null;
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryUpdateDto categoryUpdateDto) {
        return null;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryCreateDto categoryCreateDto) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }
}
