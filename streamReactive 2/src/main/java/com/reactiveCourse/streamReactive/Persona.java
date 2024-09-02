package com.reactiveCourse.streamReactive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Persona {
    private String nombre;

    private String apellido;

    private String telefono;

    private int edad;

    private String signo;
}
