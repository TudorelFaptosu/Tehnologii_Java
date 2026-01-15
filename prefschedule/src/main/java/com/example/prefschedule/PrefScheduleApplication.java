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

    // --- ADAUGĂ ACEST BEAN ---
    // Aceasta va șterge TOATĂ baza de date la fiecare pornire și o va recrea de la zero.
    // Rezolvă garantat erorile "Checksum mismatch" și "Missing column".
    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            flyway.clean();   // Șterge tot (drop schema)
            flyway.migrate(); // Recreează tabelele cu noile scripturi
        };
    }
}