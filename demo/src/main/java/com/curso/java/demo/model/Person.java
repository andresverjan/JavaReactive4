package com.curso.java.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Table("person")
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

        public Person() {
        }

}
