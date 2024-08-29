package org.example.personas;



public class Personas {
    private String nombre;
    private String apellido;
    private String id;
    private int edad;
    private String signoZodiacal;
    private String telefono;


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSignoZodiacal(String signoZodiacal) {
        this.signoZodiacal = signoZodiacal;
    }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getId() {
        return id;
    }

    public int getEdad() {
        return edad;
    }

    public String getSignoZodiacal() {
        return signoZodiacal;
    }
    public String getTelefono() {
        return telefono;
    }

    public Personas(String nombre, String apellido, String id, int edad, String signoZodiacal, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.edad = edad;
        this.signoZodiacal = signoZodiacal;
        this.telefono = telefono;
    }
}
