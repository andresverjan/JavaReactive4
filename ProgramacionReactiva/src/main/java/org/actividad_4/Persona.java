package org.actividad_4;


import java.util.List;

public class Persona {
    private String nombre;

    private String apellido;

    private List<List<String>> telefonos;

    private int edad;

    private String signo;

    public Persona(String nombre, String apellido, List<List<String>> telefonos, int edad, String signo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefonos = telefonos;
        this.edad = edad;
        this.signo = signo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public List<List<String>> getTelefonos() {
        return telefonos;
    }

    public int getEdad() {
        return edad;
    }

    public String getSigno() {
        return signo;
    }
}
