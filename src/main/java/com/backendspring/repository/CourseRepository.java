package com.backendspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendspring.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{ }
