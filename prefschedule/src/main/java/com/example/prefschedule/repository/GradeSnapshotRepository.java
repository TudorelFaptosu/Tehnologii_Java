package com.example.prefschedule.repository;

import com.example.prefschedule.patterns.es.GradeSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeSnapshotRepository extends JpaRepository<GradeSnapshot, Long> {

    // Această metodă este apelată în GradeEventService pentru a găsi snapshot-ul
    GradeSnapshot findByStudentIdAndCourseId(Long studentId, Long courseId);

}