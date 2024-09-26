package com.example.demo.model;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column("name")
    private String name;

    @NotNull(message = "La edad no puede estar vacía")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 150, message = "La edad no puede ser mayor que 150")
    @Column("age")
    private Integer age;

    @NotNull(message = "El género no puede estar vacío")
    @Pattern(regexp = "M|F", message = "El género debe ser 'M' o 'F'")
    @Column("gender")
    private String gender;

    @NotNull(message = "La fecha de nacimiento no puede estar vacía")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "El tipo de sangre no puede estar vacío")
    @Pattern(regexp = "A\\+|A-|B\\+|B-|AB\\+|AB-|O\\+|O-", message = "Tipo de sangre inválido")
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