package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Courses {
    public enum Type { COMPULSORY, OPTIONAL }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;


    private String code;
    private String abbr;
    private String name;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructors instructor;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Packs pack;

    @Column(name = "group_count")
    private Integer groupCount;

    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getAbbr() { return abbr; }
    public void setAbbr(String abbr) { this.abbr = abbr; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Instructors getInstructor() { return instructor; }
    public void setInstructor(Instructors instructor) { this.instructor = instructor; }
    public Packs getPack() { return pack; }
    public void setPack(Packs pack) { this.pack = pack; }
    public Integer getGroupCount() { return groupCount; }
    public void setGroupCount(Integer groupCount) { this.groupCount = groupCount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}
