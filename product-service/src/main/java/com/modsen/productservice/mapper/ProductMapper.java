package com.modsen.productservice.mapper;

import com.modsen.productservice.domain.Category;
import com.modsen.productservice.domain.Product;
import com.modsen.productservice.dto.CategoryForProductResponseDto;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductCreateDto;
import com.modsen.productservice.dto.ProductResponseDto;
import com.modsen.productservice.dto.ProductStandaloneCreateDto;
import com.modsen.productservice.dto.ProductUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    Product fromProductCreateDtoToProduct(ProductCreateDto productCreateDto);

    @Mapping(target = "categoryForProductResponseDto", source = "product.category")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    ProductResponseDto toProductResponseDto(Product product);

    Product fromProductUpdateDtoToProduct(ProductUpdateDto productUpdateDto);

    Product fromProductStandaloneCreateDtoToProduct(ProductStandaloneCreateDto productStandaloneCreateDto);

    @Mapping(target = "pageNum", source = "number")
    PageContainerDto<ProductResponseDto> toProductPageContainerDto(Page<Product> productResponse);

    @Mapping(target = "categoryId", source = "id")
    @Mapping(target = "categoryName", source = "name")
    CategoryForProductResponseDto categoryToCategoryForProductResponseDto(Category category);
}
