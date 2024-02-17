package com.backendspring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backendspring.model.Course;
import com.backendspring.repository.CourseRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return this.courseRepository.findById(id);
    }

    public Course create(Course course) {
        return this.courseRepository.save(course);
    }

    public Optional<Course> update(Long id, Course course) {
        return this.courseRepository.findById(id)
            .map(recordFound -> {
                recordFound.setName(course.getName());
                recordFound.setCategory(course.getCategory());
                return this.courseRepository.save(recordFound);
            });
    }

    public boolean delete(Long id) {
        return this.courseRepository.findById(id)
            .map(recordFound -> {
                this.courseRepository.delete(recordFound);
                return true;
            }).orElse(false);
    }
}
