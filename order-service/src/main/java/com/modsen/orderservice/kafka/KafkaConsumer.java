package com.modsen.orderservice.kafka;

import com.modsen.orderservice.dto.ProductResponseForOrderDto;
import com.modsen.orderservice.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final OrderItemService orderItemService;
    private final static String PRODUCT_DATA_TOPIC = "product-data-transfer-topic";

    @RetryableTopic
    @KafkaListener(topics = "product-data-transfer-topic")
    @Transactional
    public void consume(ProductResponseForOrderDto productResponseForOrderDto) {
        log.info(productResponseForOrderDto.toString());
        orderItemService.updateOrderItemData(productResponseForOrderDto);
    }

    @DltHandler
    public void consumeDLT() {
        log.error("Something went wrong");
    }
}
