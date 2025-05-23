package com.modsen.productservice.repository;

import com.modsen.productservice.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE  cast(c.id as string) like lower(concat('%',:keyword,'%')) or LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Category> findAll(Pageable pageable, @Param("keyword") String keyword);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Category> findByProductsIsNotEmptyOrderByNameAsc();
}
