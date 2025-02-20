package com.modsen.productservice.service.impl;

import com.modsen.productservice.domain.Category;
import com.modsen.productservice.domain.Product;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductCreateDto;
import com.modsen.productservice.dto.ProductResponseDto;
import com.modsen.productservice.dto.ProductStandaloneCreateDto;
import com.modsen.productservice.dto.ProductUpdateDto;
import com.modsen.productservice.exception.ProductNotFound;
import com.modsen.productservice.exception.ResourceAlreadyExistsException;
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
        return productMapper.toProductResponseDto(getProduct(id));
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productMapper.toProductResponseDtoList(productRepository.findAll());
    }

    @Override
    public PageContainerDto<ProductResponseDto> findAll(Pageable pageable, String keyword) {
        Page<Product> productPage = productRepository.findAll(pageable, keyword);
        return productMapper.toProductPageContainerDto(productPage);
    }

    @Override
    public ProductResponseDto updateProduct(ProductUpdateDto productUpdateDto, Category category) {
        Product updatedProduct = productMapper.fromProductUpdateDtoToProduct(productUpdateDto);

        if (productRepository.existsByNameAndIdNot(updatedProduct.getName(), updatedProduct.getId())) {
            throw new ResourceAlreadyExistsException("Product with name " + updatedProduct.getName() + " already exists");
        }

        Product product = getProduct(updatedProduct.getId());
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());

        if(updatedProduct.getCategory() != null) {
            product.setCategory(category);
        }
        return productMapper.toProductResponseDto(productRepository.save(product));
    }


    @Override
    public ProductResponseDto createProduct(ProductCreateDto productCreateDto, Category category) {
        return null;
    }

    @Override
    public ProductResponseDto createProduct(ProductStandaloneCreateDto productCreateDto, Category category) {
        Product product = productMapper.fromProductStandaloneCreateDtoToProduct(productCreateDto);
        if(productRepository.existsByName(product.getName()))
            throw new ResourceAlreadyExistsException("Product with this name already exist " + product.getName());
        product.setCategory(category);
        return productMapper.toProductResponseDto(productRepository.save(product));
    }

    //TODO Checking if product used in order
    @Override
    public void deleteProduct(Long id) {

    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product with id " + id + " not found"));
    }

}
