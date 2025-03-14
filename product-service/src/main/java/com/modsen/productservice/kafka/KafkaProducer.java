package com.modsen.productservice.kafka;

import com.modsen.productservice.dto.ProductResponseForOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final static String PRODUCT_DATA_TOPIC = "product-data-transfer-topic";
    private final static String MESSAGE_SENT = "Sent message successfully{}with offset{}";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendActualData(ProductResponseForOrderDto productResponseForOrderDto) {
        sendMessage(PRODUCT_DATA_TOPIC, generateTransactionalKey(), productResponseForOrderDto);
    }

    private void sendMessage(String topic, String key, Object message) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(topic, key, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info(MESSAGE_SENT,
                        message.toString(),
                        result.getRecordMetadata().offset());
            } else {
                log.error(ex.getMessage());
            }
        });
    }

    private String generateTransactionalKey() {
        return UUID.randomUUID().toString();
    }
}
