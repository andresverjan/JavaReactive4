package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Curso {
    private String name;
    private List<Modulo> modules;
}
