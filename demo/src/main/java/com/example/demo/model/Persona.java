package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Getter
@Setter
public class Persona {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("age")
    private Integer age;
    @Column("gender")
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    @Column("blood_type")
    private String bloodType;

    public Persona(Long id, String name, Integer age, String gender, LocalDate dateOfBirth, String bloodType) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
    }

}