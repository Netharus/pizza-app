package com.modsen.productservice.service.impl;

import com.modsen.productservice.domain.Category;
import com.modsen.productservice.dto.CategoryCreateDto;
import com.modsen.productservice.dto.CategoryResponseDto;
import com.modsen.productservice.dto.CategoryUpdateDto;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductForCategoryResponseDto;
import com.modsen.productservice.exception.CategoryNotFoundException;
import com.modsen.productservice.exception.ResourceAlreadyExistsException;
import com.modsen.productservice.mapper.CategoryMapper;
import com.modsen.productservice.repository.CategoryRepository;
import com.modsen.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryResponseDtoById(Long id) {
        return categoryMapper.categoryToCategoryResponseDto(getCategory(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return getCategory(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAll() {
        return categoryMapper.toCategoryResponseDtoList(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PageContainerDto<CategoryResponseDto> findAll(Pageable pageable, String keyword) {
        return categoryMapper.toCategoryPageContainerDto(categoryRepository.findAll(pageable, keyword));
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryUpdateDto categoryUpdateDto) {
        Category updateCategory = categoryMapper.fromCategoryUpdateDtoToCategory(categoryUpdateDto);

        if (categoryRepository.existsByNameAndIdNot(updateCategory.getName(), updateCategory.getId())) {
            throw new ResourceAlreadyExistsException("Category with the name " + updateCategory.getName() + " already exists");
        }

        Category category = getCategory(updateCategory.getId());

        category.setName(updateCategory.getName());

        return categoryMapper.categoryToCategoryResponseDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public Category createCategory(CategoryCreateDto categoryCreateDto) {
        Category category = categoryMapper.fromCategoryCreateDtoToCategory(categoryCreateDto);
        if (categoryRepository.existsByName(category.getName())) {
            throw new ResourceAlreadyExistsException("Category with the name " + category.getName() + " already exists");
        }
        category.getProducts().clear();
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        getCategory(id);
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryResponseDto(Category category, List<ProductForCategoryResponseDto> products) {
        return categoryMapper.categoryToCategoryResponseDto(category, products);
    }

    @Transactional
    protected Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found"));
    }


}
