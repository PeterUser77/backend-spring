package com.backendspring.dto;

import java.util.List;


public record CoursePageDTO(
    List<CourseDTO> courses,
    Integer totalPages,
    Long totalElements
) { }
