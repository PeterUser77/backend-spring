package com.backendspring.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
@Where(clause = "STATUS = 'active'")
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
    private List<Lesson> lessons = new ArrayList<>();

    public Course() {
    }

    public Course(
        Long id, 
        @NotNull @NotBlank @Length(min = 2, max = 50) String name, 
        @NotNull Category category,
        @NotNull Status status, 
        @NotBlank @NotNull @NotEmpty List<Lesson> lessons
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.status = status;
        this.lessons = lessons;
    }

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

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    

}
