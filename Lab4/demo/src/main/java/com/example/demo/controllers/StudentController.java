package com.example.demo.controllers;

import com.example.demo.dto.StudentDTO; // Asigură-te că DTO-ul e importat corect
import com.example.demo.model.Role;     // Import necesar pentru Role
import com.example.demo.model.Student;
import com.example.demo.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- 1. Import necesar
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Students", description = "Operations related to students")
public class StudentController {

    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder; // <--- 2. Injectăm Encoder-ul

    // 3. Adăugăm passwordEncoder în constructorul controller-ului
    public StudentController(StudentService studentService, PasswordEncoder passwordEncoder) {
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @Operation(summary = "Get all students")
    public CollectionModel<EntityModel<Student>> getAllStudents() {
        List<EntityModel<Student>> students = studentService.getAllStudents().stream()
                .map(student -> EntityModel.of(student,
                        linkTo(methodOn(StudentController.class).getStudentById(student.getId())).withSelfRel(),
                        linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students")))
                .collect(Collectors.toList());

        return CollectionModel.of(students, linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public EntityModel<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);

        return EntityModel.of(student,
                linkTo(methodOn(StudentController.class).getStudentById(id)).withSelfRel(),
                linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students"),
                linkTo(methodOn(PreferenceController.class).getStudentPreferences(id)).withRel("preferences"));
    }

    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<EntityModel<Student>> createStudent(@RequestBody @Valid StudentDTO studentDTO) {
        // 4. Verificare simplă pentru parolă (să nu fie null la creare)
        String rawPassword = studentDTO.getPassword();
        if (rawPassword == null || rawPassword.isEmpty()) {
            // Putem seta o parolă default sau returna eroare, aici punem un placeholder pt demo
            rawPassword = "defaultPassword123";
        }

        // 5. Instanțiem Studentul respectând ordinea celor 6 parametri
        Student student = new Student(
                studentDTO.getName(),
                studentDTO.getEmail(),
                passwordEncoder.encode(rawPassword), // Criptăm parola
                Role.ROLE_STUDENT,                   // Setăm rolul manual
                studentDTO.getCode(),
                studentDTO.getYear()
        );

        Student createdStudent = studentService.createStudent(student);

        EntityModel<Student> entityModel = EntityModel.of(createdStudent,
                linkTo(methodOn(StudentController.class).getStudentById(createdStudent.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students"));

        return ResponseEntity.created(linkTo(methodOn(StudentController.class).getStudentById(createdStudent.getId())).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a student")
    public ResponseEntity<EntityModel<Student>> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentDTO studentDTO) {

        // La update e mai complicat cu parola.
        // Dacă DTO-ul nu are parolă, folosim una temporară doar ca să satisfacem constructorul.
        // Logica reală de păstrare a parolei vechi ar trebui să fie în Service.
        String passToUse = (studentDTO.getPassword() != null)
                ? passwordEncoder.encode(studentDTO.getPassword())
                : "NO_CHANGE";

        Student studentDetails = new Student(
                studentDTO.getName(),
                studentDTO.getEmail(),
                passToUse,            // Parola (criptată sau placeholder)
                Role.ROLE_STUDENT,    // Rolul
                studentDTO.getCode(),
                studentDTO.getYear()
        );

        Student updatedStudent = studentService.updateStudent(id, studentDetails);

        EntityModel<Student> entityModel = EntityModel.of(updatedStudent,
                linkTo(methodOn(StudentController.class).getStudentById(updatedStudent.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).getAllStudents()).withRel("students"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}