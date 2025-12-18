package com.example.demo.security;

import com.example.demo.model.Instructor;
import com.example.demo.model.Person;
import com.example.demo.model.Student;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    public CustomUserDetailsService(StudentRepository studentRepository, InstructorRepository instructorRepository) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Caută în Studenți
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return buildUserDetails(student.get());
        }

        // 2. Caută în Instructori
        Optional<Instructor> instructor = instructorRepository.findByEmail(email);
        if (instructor.isPresent()) {
            return buildUserDetails(instructor.get());
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    private UserDetails buildUserDetails(Person person) {
        return new User(
                person.getEmail(),
                person.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(person.getRole().name()))
        );
    }
}