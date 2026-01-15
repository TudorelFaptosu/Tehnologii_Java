package com.example.prefschedule.repository;

import com.example.prefschedule.patterns.es.StudentGradeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentGradeEventRepository extends JpaRepository<StudentGradeEvent, Long> {

    // Această metodă este necesară pentru linia: count % 5 == 0
    long countByStudentIdAndCourseId(Long studentId, Long courseId);

    // Această metodă este necesară pentru: findByStudentIdAndCourseIdAndIdGreaterThan
    List<StudentGradeEvent> findByStudentIdAndCourseIdAndIdGreaterThan(Long studentId, Long courseId, Long lastEventId);

    // Această metodă este necesară pentru: findTopByStudentIdAndCourseIdOrderByIdDesc
    StudentGradeEvent findTopByStudentIdAndCourseIdOrderByIdDesc(Long studentId, Long courseId);
}