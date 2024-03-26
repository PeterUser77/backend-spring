package com.backendspring.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import com.backendspring.enums.Category;
import com.backendspring.enums.Status;
import com.backendspring.enums.converter.CategoryConverter;
import com.backendspring.enums.converter.StatusConverter;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@SQLDelete(sql = "UPDATE COURSE C SET C.STATUS = 'inactive' WHERE C.ID = ?")
@SQLRestriction("STATUS <> 'inactive'")
@Entity
@Table(name = "COURSE")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
        name = "ID")
    @JsonProperty("_id")
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(
        min = 2,
        max = 50)
    @Column(
        name = "NAME", 
        length = 50, 
        nullable = false)
    private String name;

    @NotNull
    @Column(
        name = "CATEGORY", 
        length = 10, 
        nullable = false)
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @NotNull
    @Column(
        name = "STATUS",
        nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

    @NotNull
    @NotEmpty
    @Valid
    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "course")
    private Set<Lesson> lessons = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLesson(Set<Lesson> lessons) {
        if (lessons == null) {
            throw new IllegalArgumentException("Lessons cannot be null.");
        }
        this.lessons.clear();
        this.lessons.addAll(lessons);
    }

    public void addLesson(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null.");
        }
        lesson.setCourse(this);
        this.lessons.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        if (lesson == null) {
            throw new IllegalArgumentException("Lesson cannot be null.");
        }
        lesson.setCourse(null);
        this.lessons.remove(lesson);
    }

}
