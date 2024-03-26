package com.backendspring.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backendspring.dto.CourseDTO;
import com.backendspring.dto.CourseRequestDTO;
import com.backendspring.dto.LessonDTO;
import com.backendspring.enums.Category;
import com.backendspring.model.Course;
import com.backendspring.model.Lesson;

@Component
public class CourseMapper {
    
    public Course toModel(CourseRequestDTO courseRequestDTO) {

        Course course = new Course();
        course.setName(courseRequestDTO.name());
        course.setCategory(convertCategoryValue(courseRequestDTO.category()));

        Set<Lesson> lessons = courseRequestDTO.lessons().stream()
                .map(lessonDTO -> {
                    Lesson lesson = new Lesson();
                    if (lesson.getId() > 0) {
                        lesson.setId(lessonDTO._id());
                    }
                    lesson.setName(lessonDTO.name());
                    lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
                    lesson.setCourse(course);
                    return lesson;
                }).collect(Collectors.toSet());
        course.setLesson(lessons);

        return course;
    }

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }
        List<LessonDTO> lessonDTOList = course.getLessons()
                .stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
                .toList();
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(),
                lessonDTOList);
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "front-end" -> Category.FRONT_END;
            case "back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Invalid Category.");
        };
    }

    public Lesson convertLessonDTOToLesson(LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        lesson.setId(lessonDTO._id());
        lesson.setName(lessonDTO.name());
        lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
        return lesson;
    }

}
