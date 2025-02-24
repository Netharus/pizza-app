package com.modsen.productservice.service.impl;

import com.modsen.productservice.domain.Category;
import com.modsen.productservice.domain.Product;
import com.modsen.productservice.dto.PageContainerDto;
import com.modsen.productservice.dto.ProductCreateDto;
import com.modsen.productservice.dto.ProductForCategoryResponseDto;
import com.modsen.productservice.dto.ProductRequestDto;
import com.modsen.productservice.dto.ProductResponseDto;
import com.modsen.productservice.dto.ProductResponseForOrderDto;
import com.modsen.productservice.dto.ProductStandaloneCreateDto;
import com.modsen.productservice.dto.ProductUpdateDto;
import com.modsen.productservice.exception.ProductNotFoundException;
import com.modsen.productservice.exception.ResourceAlreadyExistsException;
import com.modsen.productservice.exception.ResourceNotAvailable;
import com.modsen.productservice.mapper.ProductMapper;
import com.modsen.productservice.repository.ProductRepository;
import com.modsen.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto findById(Long id) {
        return productMapper.toProductResponseDto(getProduct(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll() {
        return productMapper.toProductResponseDtoList(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PageContainerDto<ProductResponseDto> findAll(Pageable pageable, String keyword) {
        Page<Product> productPage = productRepository.findAll(pageable, keyword);
        return productMapper.toProductPageContainerDto(productPage);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(ProductUpdateDto productUpdateDto, Category category) {
        Product updatedProduct = productMapper.fromProductUpdateDtoToProduct(productUpdateDto);

        if (productRepository.existsByNameAndIdNot(updatedProduct.getName(), updatedProduct.getId())) {
            throw new ResourceAlreadyExistsException("Product with name " + updatedProduct.getName() + " already exists");
        }

        Product product = getProduct(updatedProduct.getId());
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());

        if (updatedProduct.getCategory() != null) {
            product.setCategory(category);
        }
        return productMapper.toProductResponseDto(productRepository.save(product));
    }


    @Override
    @Transactional
    public ProductForCategoryResponseDto createProduct(ProductCreateDto productCreateDto, Category category) {
        Product product = productMapper.fromProductCreateDtoToProduct(productCreateDto);
        if (productRepository.existsByName(product.getName()))
            throw new ResourceAlreadyExistsException("Product with this name already exist " + product.getName());
        product.setCategory(category);
        return productMapper.productToCategoryResponseDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductStandaloneCreateDto productCreateDto, Category category) {
        Product product = productMapper.fromProductStandaloneCreateDtoToProduct(productCreateDto);
        if (productRepository.existsByName(product.getName()))
            throw new ResourceAlreadyExistsException("Product with this name already exist " + product.getName());
        product.setCategory(category);
        return productMapper.toProductResponseDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        getProduct(id);
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProductResponseForOrderDto getProductData(ProductRequestDto productRequestDto) {
        checkAvailability(productRequestDto.productIds());
        return new ProductResponseForOrderDto(productRequestDto
                .productIds()
                .stream()
                .map(this::getProduct)
                .collect(Collectors
                        .toMap(Product::getId,
                                product -> new ProductResponseForOrderDto
                                        .ProductData(product.getPrice(), product.getName()))));
    }

    @Override
    @Transactional
    public ProductResponseDto changeStatus(Long id) {
        Product product = getProduct(id);
        product.setAvailable(!product.getAvailable());
        return productMapper.toProductResponseDto(productRepository.save(product));
    }


    @Transactional
    protected Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    protected void checkAvailability(List<Long> productIds) {
        productIds.forEach(productId -> {
            if (!productRepository.existsByIdAndAvailableIsTrue(productId)) {
                throw new ResourceNotAvailable("Product with id:" + productId + " is no longer available");
            }
        });
    }
}
