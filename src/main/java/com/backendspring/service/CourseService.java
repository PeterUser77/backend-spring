package com.backendspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.backendspring.dto.CourseDTO;
import com.backendspring.dto.CoursePageDTO;
import com.backendspring.dto.mapper.CourseMapper;
import com.backendspring.exception.RecordNotFoundException;
import com.backendspring.model.Course;
import com.backendspring.repository.CourseRepository;

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

    public CourseDTO findById(Long id) {
        return this.courseRepository.findById(id)
            .map(courseMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(CourseDTO courseDTO) {
        return this.courseMapper.toDTO(
            this.courseRepository.save(
                this.courseMapper.toEntity(courseDTO)));
    }

    public CourseDTO update(Long id, CourseDTO courseDTO) {
        return this.courseRepository.findById(id)
            .map(recordFound -> {
                Course course = this.courseMapper.toEntity(courseDTO);
                
                recordFound.setName(courseDTO.name());
                recordFound.setCategory(this.courseMapper.convertCategoryValue(courseDTO.category()));
                
                recordFound.getLessons().clear();
                course.getLessons().forEach(recordFound.getLessons()::add);
                
                return this.courseMapper.toDTO(this.courseRepository.save(recordFound));
            }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(Long id) {
        this.courseRepository.delete(
            this.courseRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id))  
        );
    }
}
