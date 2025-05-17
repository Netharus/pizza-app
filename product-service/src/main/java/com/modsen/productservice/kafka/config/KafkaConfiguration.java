package com.modsen.productservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    private static final String PRODUCT_TOPIC = "product-data-transfer-topic";

    @Bean
    public NewTopic productNewTopic() {
        return new NewTopic(PRODUCT_TOPIC, 3, (short) 1);
    }
}
