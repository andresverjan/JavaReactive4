package com.company;

import java.util.function.Function;

public class Main {

    public static <T, R> Persona unirNombresApellidos(T persona, Function<T, R> funcion){
        return (Persona) funcion.apply(persona);
    }

    public static void main(String[] args) {
            Persona persona = new Persona();

            persona.nombre = "Alvaro";
            persona.apellido = "Manjarres";

            persona  =  unirNombresApellidos(persona, Persona::concatenarNombresApellidos);
            System.out.println(persona.nombre);
            System.out.println(persona.apellido);
    }
}

  class Persona {
    public String nombre;
    public String apellido;

    public static Persona concatenarNombresApellidos(Persona persona){
        String nombreApellido = persona.nombre + persona.apellido;
        String apellidoNombre = persona.apellido + persona.nombre;
        persona.nombre = nombreApellido;
        persona.apellido = apellidoNombre;
        return persona;
    }
}
