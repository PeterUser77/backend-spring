package com.backendspring.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CourseDTO(
    
    @JsonProperty("_id") Long id,
    @NotNull @NotBlank @Length(min = 2, max = 50) String name,
    @NotNull @NotBlank @Pattern(regexp = "back-end|front-end") String category,
    List<LessonDTO> lessons) { }
