package com.modsen.productservice.mapper;

import com.modsen.productservice.domain.Category;
import com.modsen.productservice.domain.Product;
import com.modsen.productservice.dto.CategoryCreateDto;
import com.modsen.productservice.dto.CategoryResponseDto;
import com.modsen.productservice.dto.CategoryUpdateDto;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductForCategoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    CategoryResponseDto categoryToCategoryResponseDto(Category category);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "products", source = "products")
    CategoryResponseDto categoryToCategoryResponseDto(Category category, List<ProductForCategoryResponseDto> products);

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "productName", source = "name")
    ProductForCategoryResponseDto productToCategoryResponseDto(Product product);

    @Mapping(target = "pageNum", source = "number")
    PageContainerDto<CategoryResponseDto> toCategoryPageContainerDto(Page<Category> categoryPage);

    Category fromCategoryCreateDtoToCategory(CategoryCreateDto categoryCreateDto);

    Category fromCategoryUpdateDtoToCategory(CategoryUpdateDto categoryUpdateDto);

    List<CategoryResponseDto> toCategoryResponseDtoList(List<Category> categoryList);
}
