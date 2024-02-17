package com.backendspring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    @Pattern(regexp = "back-end|front-end")
    @Column(
        name = "CATEGORY", 
        length = 10, 
        nullable = false)
    private String category;

    @NotNull
    @NotBlank
    @Pattern(regexp = "active|inactive")
    @Column(length = 10, nullable = false)
    private String status = "active";

}
