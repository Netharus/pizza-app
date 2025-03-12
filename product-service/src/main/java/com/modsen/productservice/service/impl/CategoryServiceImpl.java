package com.modsen.productservice.service.impl;

import com.modsen.productservice.client.OrderClient;
import com.modsen.productservice.domain.Category;
import com.modsen.productservice.domain.Product;
import com.modsen.productservice.dto.CategoryCreateDto;
import com.modsen.productservice.dto.CategoryResponseDto;
import com.modsen.productservice.dto.CategoryUpdateDto;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductForCategoryResponseDto;
import com.modsen.productservice.exception.CategoryNotFoundException;
import com.modsen.productservice.exception.ErrorMessages;
import com.modsen.productservice.exception.ResourceAlreadyExistsException;
import com.modsen.productservice.exception.ResourceNotAvailable;
import com.modsen.productservice.mapper.CategoryMapper;
import com.modsen.productservice.repository.CategoryRepository;
import com.modsen.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final OrderClient orderClient;

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
            throw new ResourceAlreadyExistsException(String.format(ErrorMessages.CATEGORY_ALREADY_EXIST, updateCategory.getName()));
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
            throw new ResourceAlreadyExistsException(String.format(ErrorMessages.CATEGORY_ALREADY_EXIST, category.getName()));
        }
        category.getProducts().clear();
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategory(id);
        isProductAvailableToDelete(category.getProducts());
        categoryRepository.deleteById(id);
    }

    public void isProductAvailableToDelete(List<Product> products) {
        List<Long> productIdsUsedIn = new ArrayList<>();
        products.forEach(product -> {
            if (orderClient.isProductUsed(product.getId()).getBody()) {
                productIdsUsedIn.add(product.getId());
            }
        });
        if (!productIdsUsedIn.isEmpty()) {
            throw new ResourceNotAvailable(String.format(ErrorMessages.PRODUCT_UNAVAILABLE_TO_DELETE_CAT, productIdsUsedIn));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryResponseDto(Category category, List<ProductForCategoryResponseDto> products) {
        return categoryMapper.categoryToCategoryResponseDto(category, products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getCategoriesNameList() {
        return categoryRepository.findCategoryByOrderByNameAsc().stream().map(Category::getName).toList();
    }

    @Transactional
    protected Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(String.format(ErrorMessages.CATEGORY_NOT_FOUND, id)));
    }


}
