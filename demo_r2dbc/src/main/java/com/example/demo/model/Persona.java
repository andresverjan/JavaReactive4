package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Persona {
    private String nombre;
    private String apellido;
    private String telefono;
    private int edad;
    private String signo;
}
