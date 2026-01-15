package com.example.prefschedule.patterns.cqrs;

import com.example.prefschedule.model.Course;
import com.example.prefschedule.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseCommandService {

    private final CourseRepository writeRepository; // Postgres
    private final CourseReadRepository readRepository;   // Mongo

    public CourseCommandService(CourseRepository writeRepo, CourseReadRepository readRepo) {
        this.writeRepository = writeRepo;
        this.readRepository = readRepo;
    }

    @Transactional
    public void createCourse(Course course) {
        // 1. Write to Relational DB (Command Side)
        Course saved = writeRepository.save(course);

        // 2. Sync to NoSQL (Read Side) - In real microservices, send via Kafka
        // Here we do straightforward sync for the simple scenario
        CourseReadModel readModel = new CourseReadModel();
        readModel.setId(String.valueOf(saved.getId()));
        readModel.setName(saved.getName());
        readModel.setDescription(saved.getDescription());
        if(saved.getInstructor() != null) {
            readModel.setInstructorName(saved.getInstructor().getName());
        }

        readRepository.save(readModel);
    }
}