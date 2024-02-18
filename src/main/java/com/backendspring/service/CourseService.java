package com.backendspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backendspring.dto.CourseDTO;
import com.backendspring.dto.mapper.CourseMapper;
import com.backendspring.exception.RecordNotFoundException;
import com.backendspring.repository.CourseRepository;

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

    public List<CourseDTO> findAll() {
        return this.courseRepository.findAll()
            .stream()
            .map(courseMapper::toDTO)
            .collect(Collectors.toList());
    }

    public CourseDTO findById(Long id) {
        return this.courseRepository.findById(id)
            .map(courseMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(CourseDTO course) {
        return this.courseMapper.toDTO(this.courseRepository.save(this.courseMapper.toEntity(course)));
    }

    public CourseDTO update(Long id, CourseDTO course) {
        return this.courseRepository.findById(id)
            .map(recordFound -> {
                recordFound.setName(course.name());
                recordFound.setCategory(course.category());
                
                return this.courseMapper.toDTO(this.courseRepository.save(recordFound));
            }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(Long id) {
        this.courseRepository.delete(
            this.courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id))  
        );
    }
}
