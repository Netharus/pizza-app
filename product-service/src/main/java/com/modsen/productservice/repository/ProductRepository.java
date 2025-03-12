package com.modsen.productservice.repository;

import com.modsen.productservice.domain.Product;
import com.modsen.productservice.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findAll(Pageable pageable, @Param("keyword") String keyword);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByIdAndAvailableIsTrue(Long id);

    @Query("SELECT p FROM Product p WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND p.available = true")
    Page<Product> findAllByAvailableIsTrue(Pageable pageable, @Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND p.available = true")
    List<Product> findByOrderByCategoryAsc(String keyword);
}
