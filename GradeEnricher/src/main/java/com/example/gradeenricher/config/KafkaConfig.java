package com.example.gradeenricher.config; // <--- ATENTIE LA PACHET

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    // Topicurile pe care le foloseste acest proiect
    @Bean
    public NewTopic studentEnrichedTopic() {
        return TopicBuilder.name("student-enriched-grades")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic finalGradesTopic() {
        return TopicBuilder.name("final-grades")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // Configurare explicita a fabricii de produceri
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Instantiem manual serializer-ele pentru a evita erorile
        JsonSerializer<Object> jsonSerializer = new JsonSerializer<>();

        return new DefaultKafkaProducerFactory<>(
                configProps,
                new StringSerializer(),
                jsonSerializer
        );
    }

    // Bean-ul pe care il cauta PipelineService si nu il gasea
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}