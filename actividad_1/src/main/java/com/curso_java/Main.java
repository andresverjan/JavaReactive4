package com.curso_java;

import java.util.function.Function;

/*
Crear una función pura que reciba un objeto Persona con nombre y apellidos.
Concatenarle el apellido al nombre y el nombre al apellido
Pasar esta función pura a otra de orden superior
que debe retornar un objeto Persona con el nombre y el apellido concatenado.
 */
public class Main {
    public static void main(String[] args) {

        Persona persona = new Persona("Daniela", "Guzman");

        Persona nuevaPersona = aplicarFuncionConcatenacion(Main::concatenarNombreApellido, persona);

        System.out.println("Nuevo Nombre: " + nuevaPersona.getNombre());
        System.out.println("Nuevo Apellido: " + nuevaPersona.getApellido());
    }
    // Función pura que concatena nombre y apellido
    public  static Persona concatenarNombreApellido(Persona persona) {
        String nombreApellido = persona.getNombre() + " " + persona.getApellido();
        String apellidoNombre = persona.getApellido() + " " + persona.getNombre();
        return new Persona(nombreApellido, apellidoNombre);
    }
    // Función de orden superior que recibe una función y un objeto Persona
    public static Persona aplicarFuncionConcatenacion(Function<Persona, Persona> funcion, Persona persona) {
        return funcion.apply(persona);
    }
}