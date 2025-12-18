package com.example.demo.controllers;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Role; // <--- 1. Importul pentru Role (ESENȚIAL)
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
// import com.example.demo.repository.PersonRepository; // Nu e neapărat necesar dacă verifici direct pe Student
import com.example.demo.services.JwtService; // Sau JwtUtils, depinde cum l-ai numit
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; // Asigură-te că numele clasei corespunde (JwtUtils vs JwtService)

    public AuthController(AuthenticationManager authenticationManager,
                          StudentRepository studentRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Verifică metoda din serviciul tău: generateJwtToken(authentication) sau generateJwtToken(username)
        String jwt = jwtService.generateJwtToken(authentication);

        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody RegisterRequest signUpRequest) {
        // E mai sigur să verifici direct în repo-ul de studenți
        if (studentRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // --- AICI AM CORECTAT CONSTRUCTORUL ---
        Student student = new Student(
                signUpRequest.getName(),                       // 1. Name
                signUpRequest.getEmail(),                      // 2. Email
                passwordEncoder.encode(signUpRequest.getPassword()), // 3. Password (Criptată)
                Role.ROLE_STUDENT,                             // 4. Role (Adăugat manual)
                signUpRequest.getCode(),                       // 5. Code
                signUpRequest.getYear()                        // 6. Year
        );

        studentRepository.save(student);

        return ResponseEntity.ok("Student registered successfully!");
    }
}