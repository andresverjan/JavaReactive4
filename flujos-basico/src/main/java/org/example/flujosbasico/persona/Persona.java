package org.example.flujosbasico.persona;

public class Persona {

    private String nombre;
    private String apellido;
    private String numeroIdentificacion;
    private Integer edad;
    private String signo;

    public Persona(String nombre, String apellido, String numeroIdentificacion, Integer edad, String signo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroIdentificacion = numeroIdentificacion;
        this.edad = edad;
        this.signo = signo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public Integer getEdad() {
        return edad;
    }

    public String getSigno() {
        return signo;
    }
}
