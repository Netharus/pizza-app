package com.modsen.productservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic productNewTopic() {
        return new NewTopic("product-data-transfer-topic", 3, (short) 1);
    }
}
