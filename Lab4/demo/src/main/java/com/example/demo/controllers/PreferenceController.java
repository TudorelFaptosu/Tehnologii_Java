package com.example.demo.controllers;

import com.example.demo.dto.PreferenceDTO;
import com.example.demo.model.Preference;
import com.example.demo.services.PreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
@Tag(name = "Preferences", description = "Operations related to course preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping("/students/{studentId}/preferences")
    @Operation(summary = "Get preferences for a specific student")
    public CollectionModel<EntityModel<Preference>> getStudentPreferences(@PathVariable Long studentId) {
        List<EntityModel<Preference>> preferences = preferenceService.getPreferencesByStudentId(studentId).stream()
                .map(preference -> EntityModel.of(preference,
                        linkTo(methodOn(StudentController.class).getStudentById(studentId)).withRel("student")))
                .collect(Collectors.toList());

        return CollectionModel.of(preferences, 
                linkTo(methodOn(PreferenceController.class).getStudentPreferences(studentId)).withSelfRel());
    }

    @PostMapping("/students/{studentId}/preferences")
    @Operation(summary = "Submit a preference for a student")
    public ResponseEntity<EntityModel<Preference>> addPreference(@PathVariable Long studentId, 
                                                                 @RequestBody @Valid PreferenceDTO preferenceDTO) {
        Preference preference = preferenceService.savePreference(studentId, preferenceDTO.getCourseId(), preferenceDTO.getRank());
        
        EntityModel<Preference> entityModel = EntityModel.of(preference,
                linkTo(methodOn(PreferenceController.class).getStudentPreferences(studentId)).withRel("preferences"),
                linkTo(methodOn(StudentController.class).getStudentById(studentId)).withRel("student"));
        
        return ResponseEntity.created(linkTo(methodOn(PreferenceController.class).getStudentPreferences(studentId)).toUri())
                .body(entityModel);
    }
}
