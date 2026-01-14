package com.example.gradeenricher.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PipelineTopicConfig {

    @Bean
    public NewTopic rawGradesTopic() {
        return TopicBuilder.name("raw-grades").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic studentEnrichedTopic() {
        return TopicBuilder.name("student-enriched-grades").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic finalGradesTopic() {
        return TopicBuilder.name("final-grades").partitions(3).replicas(1).build();
    }
}