package com.example.demo.repository;

import com.example.demo.dto.CourseSearchCriteria;
import com.example.demo.model.Course;
import com.example.demo.model.Instructor;
import com.example.demo.model.Pack;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository; // Annotation importanta

import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepositoryImpl implements CourseCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Course> findCoursesByCriteria(CourseSearchCriteria criteria) {
        // 1. Initialization
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = cb.createQuery(Course.class);

        // 2. Root definition (SELECT * FROM courses)
        Root<Course> courseRoot = query.from(Course.class);

        // 3. Joins (Legaturi cu tabelele Packs si Instructors)
        // Folosim JoinType.LEFT pentru a aduce cursurile chiar daca lipsesc date in relatii (siguranta)
        Join<Course, Pack> packJoin = courseRoot.join("pack", JoinType.LEFT);
        Join<Course, Instructor> instructorJoin = courseRoot.join("instructor", JoinType.LEFT);

        // 4. Construirea listei de conditii (WHERE clause)
        List<Predicate> predicates = new ArrayList<>();

        // -- Filtru: AN --
        if (criteria.getYear() != null) {
            predicates.add(cb.equal(packJoin.get("year"), criteria.getYear()));
        }

        // -- Filtru: SEMESTRU --
        if (criteria.getSemester() != null) {
            predicates.add(cb.equal(packJoin.get("semester"), criteria.getSemester()));
        }

        // -- Filtru: NUME INSTRUCTOR (Case Insensitive & Partial Match) --
        if (criteria.getInstructorName() != null && !criteria.getInstructorName().isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(instructorJoin.get("name")),
                    "%" + criteria.getInstructorName().toLowerCase() + "%"
            ));
        }

        // 5. Aplicarea conditiilor
        query.where(predicates.toArray(new Predicate[0]));

        // Optional: Ordonare dupa nume
        query.orderBy(cb.asc(courseRoot.get("name")));

        // 6. Executia query-ului
        return entityManager.createQuery(query).getResultList();
    }
}