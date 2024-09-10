package com.valko.actividad4.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("person")
@Setter
@Getter
public class Person {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("age")
    private Integer age;
    @Column("gender")
    private String gender;
    @Column("date_of_birth")
    private LocalDate dateOfBirth;
    @Column("blood_type")
    private String bloodType;

}