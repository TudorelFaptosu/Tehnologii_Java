package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "preferences", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id"}) // Un student voteaza un curs o singura data
})
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore // Evitam bucla infinita in JSON
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Rank-ul: 1 = Cea mai dorita. Daca doua cursuri au rank 1, sunt egale (ties).
    @Column(nullable = false)
    private Integer rank;

    public Preference() {}

    public Preference(Student student, Course course, Integer rank) {
        this.student = student;
        this.course = course;
        this.rank = rank;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }
}