package com.backendspring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.backendspring.enums.Category;
import com.backendspring.enums.Status;
import com.backendspring.enums.converter.CategoryConverter;
import com.backendspring.enums.converter.StatusConverter;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@SQLDelete(sql = "UPDATE COURSE C SET C.STATUS = 'inactive' WHERE C.ID = ?")
@Where(clause = "STATUS = 'active'")
@Data
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

}
