package com.backendspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.backendspring.dto.CourseDTO;
import com.backendspring.dto.CoursePageDTO;
import com.backendspring.dto.CourseRequestDTO;
import com.backendspring.dto.mapper.CourseMapper;
import com.backendspring.enums.Status;
import com.backendspring.exception.BusinessException;
import com.backendspring.exception.RecordNotFoundException;
import com.backendspring.model.Course;
import com.backendspring.model.Lesson;
import com.backendspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(
        CourseRepository courseRepository,
        CourseMapper courseMapper
    ) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO findAll(        
        @PositiveOrZero
        int page,
        @Positive
        @Max(10)
        int element
    ) {
        Page<Course> pageCourses = 
            this.courseRepository.findAll(PageRequest.of(page, element));

        List<CourseDTO> coursesDTO = pageCourses.get()
            .map(this.courseMapper::toDTO)
            .collect(Collectors.toList());

        return new CoursePageDTO(
            coursesDTO, 
            pageCourses.getTotalPages(),
            pageCourses.getTotalElements());
    }

    public CourseDTO findById(@NonNull Long id) {
        return this.courseRepository.findById(id)
            .map(courseMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public List<CourseDTO> findByName(String name) {
        return courseRepository.findByName(name).stream().map(courseMapper::toDTO).toList();
    }

    public CourseDTO create(@Valid CourseRequestDTO courseRequestDTO) {
        courseRepository.findByName(courseRequestDTO.name()).stream()
        .filter(c -> c.getStatus().equals(Status.ACTIVE))
        .findAny().ifPresent(c -> {
            throw new BusinessException("A course with name " + courseRequestDTO.name() + " already exists.");
        });
        
        Course course = courseMapper.toModel(courseRequestDTO);
        course.setStatus(Status.ACTIVE);
        return courseMapper.toDTO(courseRepository.save(course));
    }

    public CourseDTO update(@NonNull Long id, CourseRequestDTO courseRequestDTO) {
        return courseRepository.findById(id).map(actual -> {
            actual.setName(courseRequestDTO.name());
            actual.setCategory(courseMapper.convertCategoryValue(courseRequestDTO.category()));
            mergeLessonsForUpdate(actual, courseRequestDTO);
            return courseMapper.toDTO(courseRepository.save(actual));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NonNull Long id) {
        this.courseRepository.delete(
            this.courseRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id))  
        );
    }


    private void mergeLessonsForUpdate(Course updatedCourse, CourseRequestDTO courseRequestDTO) {
        // find the lessons that were removed
        List<Lesson> lessonsToRemove = updatedCourse.getLessons().stream()
                .filter(lesson -> courseRequestDTO.lessons().stream()
                        .anyMatch(lessonDto -> lessonDto._id() != 0 && lessonDto._id() == lesson.getId()))
                .toList();
        lessonsToRemove.forEach(updatedCourse::removeLesson);

        courseRequestDTO.lessons().forEach(lessonDto -> {
            // new lesson, add it
            if (lessonDto._id() == 0) {
                updatedCourse.addLesson(courseMapper.convertLessonDTOToLesson(lessonDto));
            } else {
                // existing lesson, find it and update
                updatedCourse.getLessons().stream()
                        .filter(lesson -> lesson.getId() == lessonDto._id())
                        .findAny()
                        .ifPresent(lesson -> {
                            lesson.setName(lessonDto.name());
                            lesson.setYoutubeUrl(lessonDto.youtubeUrl());
                        });
            }
        });
    }
}
