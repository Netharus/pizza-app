package com.modsen.productservice.mapper;

import com.modsen.productservice.domain.Category;
import com.modsen.productservice.dto.CategoryForProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "categoryId", source = "id")
    @Mapping(target = "categoryName", source = "name")
    CategoryForProductResponseDto categoryToCategoryForProductResponseDto(Category category);
}
