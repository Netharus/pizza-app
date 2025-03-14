package com.modsen.orderservice.repository;

import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where cast(o.id as string) like lower(concat('%',:keyword,'%')) " +
            "or lower(o.userId) like lower(concat('%',:keyword,'%')) " +
            "or lower(o.status) like lower(concat('%',:keyword,'%')) ")
    Page<Order> findAll(Pageable pageable, @Param("keyword") String keyword);

    Page<Order> findByUserId(String id, Pageable pageable);

    boolean existsByOrderItems_ProductIdAndStatusNotIn(Long productId, List<OrderStatus> statuses);

    boolean existsByUserIdAndStatusNotIn(String userId, List<OrderStatus> statuses);

    @Query("SELECT o FROM Order o WHERE "
            + "(LOWER(CAST(o.id AS string)) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "
            + "LOWER(o.status) LIKE LOWER(CONCAT('%',:keyword,'%'))) "
            + "AND o.userId = :userId")
    Page<Order> findAllByUserId(@Param("userId") String userId,
                                @Param("keyword") String keyword,
                                Pageable pageable);

    List<Order> findAllByUserIdAndStatusNotIn(String userId, List<OrderStatus> statuses);
}
