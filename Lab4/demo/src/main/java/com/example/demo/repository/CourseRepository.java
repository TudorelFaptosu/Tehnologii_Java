package com.example.demo.repository;


import com.example.demo.model.Courses;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseRepository extends JpaRepository<Courses, Long> {

    List<Courses> findByType(Courses.Type type);

    @Query("SELECT c FROM Courses c WHERE c.instructor.name = :name")
    List<Courses> findByInstructorName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Courses c WHERE c.pack.id = :packId")
    int deleteByPackId(@Param("packId") Long packId);
}
