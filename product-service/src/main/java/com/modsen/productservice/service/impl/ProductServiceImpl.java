package com.modsen.productservice.service.impl;

import com.modsen.productservice.domain.Product;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductCreateDto;
import com.modsen.productservice.dto.ProductResponseDto;
import com.modsen.productservice.dto.ProductUpdateDto;
import com.modsen.productservice.mapper.ProductMapper;
import com.modsen.productservice.repository.ProductRepository;
import com.modsen.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto findById(Long id) {
        return null;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return List.of();
    }

    @Override
    public PageContainerDto<ProductResponseDto> findAll(Pageable pageable, String keyword) {
        Page<Product> productPage = productRepository.findAll(pageable,keyword);
        return productMapper.toProductPageContainerDto(productPage);
    }

    @Override
    public ProductResponseDto updateProduct(ProductUpdateDto productUpdateDto) {
        return null;
    }

    @Override
    public ProductResponseDto createProduct(ProductCreateDto productCreateDto) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
