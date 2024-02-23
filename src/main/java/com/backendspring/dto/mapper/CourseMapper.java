package com.backendspring.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backendspring.dto.CourseDTO;
import com.backendspring.dto.LessonDTO;
import com.backendspring.enums.Category;
import com.backendspring.model.Course;
import com.backendspring.model.Lesson;

@Component
public class CourseMapper {
    
    public CourseDTO toDTO(Course course) {

        if (course == null) {
            return null;
        }

        List<LessonDTO> lessonDTOs = course.getLessons()
            .stream()
            .map(lesson -> new LessonDTO(
                    lesson.getId(), 
                    lesson.getName(), 
                    lesson.getYoutubeUrl())
            ).collect(Collectors.toList());

        return new CourseDTO(
            course.getId(), 
            course.getName(), 
            course.getCategory().getValue(),
            lessonDTOs);
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

        List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
            Lesson lesson = new Lesson();
            lesson.setId(lessonDTO.id());
            lesson.setName(lessonDTO.name());
            lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
            lesson.setCourse(course);
            return lesson;
        }).collect(Collectors.toList());
        
        course.setLessons(lessons);

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
