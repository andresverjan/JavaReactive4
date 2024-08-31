package co.com.bancolombia.manipulacion.flujos.basicos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Persona {
    private String nombre;
    private String apellido;
    private String documento;
    private int edad;
    private String signo;
    private String telefono;

}
