package com.backendspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backendspring.model.Course;
import com.backendspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(
        @RequestBody
        @Valid
        Course course
    ) {
        return courseRepository.save(course);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(
        @PathVariable 
        @NotNull
        @Positive
        Long id
    ) {
        return courseRepository.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Course> update(
        @PathVariable 
        @NotNull
        @Positive
        Long id, 
        @RequestBody 
        @Valid
        Course request
    ) {
        
        return courseRepository.findById(id)
            .map(recordFound -> {
                recordFound.setName(request.getName());
                recordFound.setCategory(request.getCategory());

                return ResponseEntity.ok().body(
                    courseRepository.save(recordFound));
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable 
        @NotNull
        @Positive
        Long id
    ) {
        return courseRepository.findById(id).map(
            recordFound -> {
                courseRepository.deleteById(id);
                return ResponseEntity.noContent().<Void>build();
            }).orElse(ResponseEntity.notFound().build());
    }
    
}
