package com.backendspring.controller;


import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backendspring.dto.CourseDTO;
import com.backendspring.dto.CoursePageDTO;
import com.backendspring.dto.CourseRequestDTO;
import com.backendspring.service.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping
    public CoursePageDTO findAll(
        @RequestParam(defaultValue = "0")
        @PositiveOrZero
        int page,
        @RequestParam(defaultValue = "10")
        @Positive
        @Max(15)
        int element
    ) {
        return courseService.findAll(page, element);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseDTO create(
        @RequestBody
        @Valid
        @NotNull
        CourseRequestDTO courseRequestDTO
    ) {
        return this.courseService.create(courseRequestDTO);
    }

    @GetMapping("/{id}")
    public CourseDTO findById(
        @PathVariable 
        @NonNull
        @Positive
        Long id
    ) {
        return courseService.findById(id);
    }
    
    @PutMapping("/{id}")
    public CourseDTO update(
        @PathVariable 
        @NonNull
        @Positive
        Long id, 
        @RequestBody 
        @Valid
        @NotNull
        CourseRequestDTO request
    ) {
        return this.courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @PathVariable 
        @NonNull
        @Positive
        Long id
    ) {
        courseService.delete(id);
    }
    
}
