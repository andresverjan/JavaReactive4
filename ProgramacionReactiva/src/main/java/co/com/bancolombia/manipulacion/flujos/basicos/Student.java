package co.com.bancolombia.manipulacion.flujos.basicos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class Student {
    private String nombre;
    private List<Course> courses;
}
