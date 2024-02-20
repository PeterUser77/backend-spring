package com.backendspring.dto.mapper;

import org.springframework.stereotype.Component;

import com.backendspring.dto.CourseDTO;
import com.backendspring.enums.Category;
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
            course.getCategory().getValue());
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
        course.setCategory(convertCategoryValue(courseDTO.category()));

        return course;
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }
        return switch(value) {
            case "front-end" -> Category.FRONT_END;
            case "back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Invalid category: " + value);
        };
    }

}
