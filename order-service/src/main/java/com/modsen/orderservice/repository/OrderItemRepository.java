package com.modsen.orderservice.repository;

import com.modsen.orderservice.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByProductId(Long orderId);

    List<OrderItem> findOrderItemsByProductId(Long productId);
}
