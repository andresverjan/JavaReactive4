package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Estudiante {
    private String name;
    private List<Curso> courses;

}
