package Ejercicio1;

import Ejercicio1.Persona.Persona;

import java.util.function.Function;

public class FuncionesPersona {

    //Función Pura
    public static Persona concatenarNombreApellidos(Persona persona) {
        String nuevoNombre = persona.getNombre() + " " + persona.getApellidos();
        String nuevosApellidos = persona.getApellidos() + " " + persona.getNombre();
        return new Persona(nuevoNombre, nuevosApellidos);
    }

    //Funcion de orden superior
    public static Persona aplicarFuncionSobrePersona(Persona persona, Function<Persona, Persona> funcion) {
        return funcion.apply(persona);
    }

    public static void main(String[] args) {

        Persona persona = new Persona("Dylan", "Llano");

        Persona nuevaPersona = aplicarFuncionSobrePersona(persona, FuncionesPersona::concatenarNombreApellidos);

        System.out.println(nuevaPersona);

    }
}