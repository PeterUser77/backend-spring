package com.backendspring.dto.mapper;

import org.springframework.stereotype.Component;

import com.backendspring.dto.CourseDTO;
import com.backendspring.model.Course;

@Component
public class CourseMapper {
    
    public CourseDTO toDTO(Course course) {

        if (course == null) {
            return null;
        }

        return new CourseDTO(
            course.getId(), 
            course.getName(), 
            course.getCategory());
    }

    public Course toEntity(CourseDTO courseDTO) {

        Course course = new Course();

        if (courseDTO == null) {
            return null;
        }

        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }

        course.setName(courseDTO.name());
        course.setCategory(courseDTO.category());
        course.setStatus("active");

        return course;
    }

}
