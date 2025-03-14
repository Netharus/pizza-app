package com.modsen.productservice.service.impl;

import com.modsen.productservice.client.OrderClient;
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
import com.modsen.productservice.exception.ErrorMessages;
import com.modsen.productservice.exception.ProductNotFoundException;
import com.modsen.productservice.exception.ResourceAlreadyExistsException;
import com.modsen.productservice.exception.ResourceNotAvailable;
import com.modsen.productservice.kafka.KafkaProducer;
import com.modsen.productservice.mapper.ProductMapper;
import com.modsen.productservice.repository.ProductRepository;
import com.modsen.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final OrderClient orderClient;
    private final KafkaProducer kafkaProducer;
    private final TransactionTemplate transactionTemplate;

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
    public PageContainerDto<ProductResponseDto> findAllForUser(Pageable pageable, String keyword) {
        return productMapper.toProductPageContainerDto(productRepository.findAllByAvailableIsTrue(pageable, keyword));
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(ProductUpdateDto productUpdateDto, Category category) {
        Product updatedProduct = productMapper.fromProductUpdateDtoToProduct(productUpdateDto);

        if (productRepository.existsByNameAndIdNot(updatedProduct.getName(), updatedProduct.getId())) {
            throw new ResourceAlreadyExistsException(String.format(ErrorMessages.PRODUCT_ALREADY_EXIST, updatedProduct.getName()));
        }

        Product product = getProduct(updatedProduct.getId());
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());

        if (productUpdateDto.categoryId() != null) {
            product.setCategory(category);
        }
        kafkaProducer.sendActualData(new ProductResponseForOrderDto(Map.of(product.getId(),
                new ProductResponseForOrderDto
                        .ProductData(product.getPrice(), product.getName()))));
        return productMapper.toProductResponseDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductForCategoryResponseDto createProduct(ProductCreateDto productCreateDto, Category category) {
        Product product = createAndSaveProduct(
                productMapper.fromProductCreateDtoToProduct(productCreateDto),
                category
        );
        return productMapper.productToCategoryResponseDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductStandaloneCreateDto productCreateDto, Category category) {
        Product product = createAndSaveProduct(
                productMapper.fromProductStandaloneCreateDtoToProduct(productCreateDto),
                category
        );
        return productMapper.toProductResponseDto(product);
    }

    private Product createAndSaveProduct(Product product, Category category) {
        if (productRepository.existsByName(product.getName())) {
            throw new ResourceAlreadyExistsException(
                    String.format(ErrorMessages.PRODUCT_ALREADY_EXIST, product.getName())
            );
        }
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        if (Boolean.TRUE.equals(orderClient.isProductUsed(id).getBody())) {
            changeStatusToFalse(product);
            throw new ResourceNotAvailable(String.format(ErrorMessages.PRODUCT_UNAVAILABLE_TO_DELETE, id));
        } else {
            productRepository.deleteById(id);
        }
    }

    protected void changeStatusToFalse(Product product) {
        transactionTemplate.execute(
                status -> {
                    product.setAvailable(false);
                    productRepository.save(product);
                    return null;
                }
        );
    }


    @Override
    @Transactional
    public ProductResponseForOrderDto getProductData(ProductRequestDto productRequestDto) {
        productRequestDto.productIds().forEach(this::getProduct);
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

    @Override
    @Transactional
    public List<ProductResponseDto> findAllByCategory(String keyword) {
        return productMapper.toProductResponseDtoList(productRepository.findByOrderByCategoryAsc(keyword));
    }


    @Transactional
    protected Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, id)));
    }

    @Transactional(readOnly = true)
    protected void checkAvailability(List<Long> productIds) {
        productIds.forEach(productId -> {
            if (!productRepository.existsByIdAndAvailableIsTrue(productId)) {
                throw new ResourceNotAvailable(String.format(ErrorMessages.PRODUCT_UNAVAILABLE, productId));
            }
        });
    }
}
