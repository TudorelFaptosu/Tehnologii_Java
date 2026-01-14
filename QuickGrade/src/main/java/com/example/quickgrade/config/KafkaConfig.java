package com.example.quickgrade.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer; // <--- IMPORT CRITIC

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic gradeTopic() {
        return TopicBuilder.name("raw-grades")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Nu mai configuram clasele aici, le dam direct ca obiecte mai jos
        // Asta evita erorile de deprecation sau instantiere

        JsonSerializer<Object> jsonSerializer = new JsonSerializer<>();
        // Configurare optionala: permite deserializarea libera in consumer (trusted packages)
        // jsonSerializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(
                configProps,
                new StringSerializer(),
                jsonSerializer
        );
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}