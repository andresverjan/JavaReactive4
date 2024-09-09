package co.com.bancolombia.manipulacion.flujos.basicos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Course {
    private String name;
    private List<Module> modules;
}
