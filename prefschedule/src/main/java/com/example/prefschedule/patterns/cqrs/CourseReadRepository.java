package com.example.prefschedule.patterns.cqrs;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CourseReadRepository extends MongoRepository<CourseReadModel, String> {
    // Fast search without JOINS
    List<CourseReadModel> findByNameContainingIgnoreCase(String name);
}