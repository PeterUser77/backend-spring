package com.backendspring.dto;

import java.util.List;
import java.util.Locale.Category;

import org.hibernate.validator.constraints.Length;

import com.backendspring.enums.validation.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
    @JsonProperty("_id") Long id,
    @NotNull @NotBlank @NotEmpty @Length(min = 2, max = 50) String name,
    @NotNull @NotBlank @ValueOfEnum(enumClass = Category.class) String category,
    @NotNull @NotEmpty @Valid  List<LessonDTO> lessons) { }
