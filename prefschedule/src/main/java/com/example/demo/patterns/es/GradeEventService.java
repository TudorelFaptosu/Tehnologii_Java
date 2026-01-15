package com.example.demo.patterns.es;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GradeEventService {

    private final StudentGradeEventRepository eventRepo;
    private final GradeSnapshotRepository snapshotRepo;

    public GradeEventService(StudentGradeEventRepository eventRepo, GradeSnapshotRepository snapshotRepo) {
        this.eventRepo = eventRepo;
        this.snapshotRepo = snapshotRepo;
    }

    // Command: Add Event
    public void addGradeEvent(Long studentId, Long courseId, Double value) {
        StudentGradeEvent event = new StudentGradeEvent();
        event.setStudentId(studentId);
        event.setCourseId(courseId);
        event.setGradeValue(value);
        event.setEventType("UPDATED"); // or CREATED
        event.setTimestamp(LocalDateTime.now());

        eventRepo.save(event);

        // Check if we need a snapshot (e.g., every 5 events)
        long count = eventRepo.countByStudentIdAndCourseId(studentId, courseId);
        if (count % 5 == 0) {
            createSnapshot(studentId, courseId);
        }
    }

    // Query: Rehydrate State (Get Current Grade)
    public Double getCurrentGrade(Long studentId, Long courseId) {
        // 1. Try to load snapshot
        GradeSnapshot snapshot = snapshotRepo.findByStudentIdAndCourseId(studentId, courseId);
        Double currentGrade = (snapshot != null) ? snapshot.getCurrentGrade() : 0.0;
        Long lastEventId = (snapshot != null) ? snapshot.getLastEventId() : 0L;

        // 2. Load events occurring AFTER the snapshot
        List<StudentGradeEvent> events = eventRepo.findByStudentIdAndCourseIdAndIdGreaterThan(studentId, courseId, lastEventId);

        // 3. Replay events (Projection)
        for (StudentGradeEvent e : events) {
            // Simple logic: Latest event overwrites.
            // Complex logic could be: average, weighted sum, etc.
            currentGrade = e.getGradeValue();
        }

        return currentGrade;
    }

    private void createSnapshot(Long studentId, Long courseId) {
        Double val = getCurrentGrade(studentId, courseId);
        // Find latest event ID
        Long lastId = eventRepo.findTopByStudentIdAndCourseIdOrderByIdDesc(studentId, courseId).getId();

        GradeSnapshot snapshot = snapshotRepo.findByStudentIdAndCourseId(studentId, courseId);
        if (snapshot == null) snapshot = new GradeSnapshot();

        snapshot.setStudentId(studentId);
        snapshot.setCourseId(courseId);
        snapshot.setCurrentGrade(val);
        snapshot.setLastEventId(lastId);

        snapshotRepo.save(snapshot);
    }
}