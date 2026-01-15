package com.example.prefschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;

@SpringBootApplication
public class PrefScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrefScheduleApplication.class, args);
    }

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}