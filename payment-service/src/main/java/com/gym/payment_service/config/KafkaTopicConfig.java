package com.gym.payment_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


//Esto crea los topics automáticamente cuando el servicio arranca.
//Aquí creás la clase Java que usa KafkaTemplate para mandar mensajes al tópico.
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic paymentCreatedTopic() {
        return TopicBuilder.name("payment-created-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic promotionUsedTopic() {
        return TopicBuilder.name("promotion-used-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
