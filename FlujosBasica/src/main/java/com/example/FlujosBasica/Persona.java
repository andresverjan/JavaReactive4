package com.example.FlujosBasica;

public class Persona {

    private String nombre;
    private String apellido;
    private String telefono;
    private int edad;
    private String signo;

    public Persona(String nombre, String apellido, String telefono, int edad, String signo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.edad = edad;
        this.signo = signo;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public int getEdad() { return edad; }
    public String getSigno() { return signo; }

    @Override
    public String toString() {
        return nombre + " " + apellido;
    }



}
