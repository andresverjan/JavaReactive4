package co.com.bancolombia;

import java.util.function.Function;

public class Main {

    public static Persona concatenarNombre(Persona persona){
        String nombre = persona.getNombre() + " " + persona.getApellido();
        String apellido = persona.getApellido() + " " + persona.getNombre();
        return new Persona(nombre,apellido);
    }

    public static Persona procesarNombres(Persona persona, Function<Persona, Persona> function){
        return function.apply(persona);
    }
    public static void main(String[] args) {
        Persona persona = new Persona("Pepito","Perez");

        Persona personaProcesada = procesarNombres(persona,Main::concatenarNombre);

        System.out.println("Nombre: " + personaProcesada.getNombre());
        System.out.println("Apellido: " + personaProcesada.getApellido());
    }
}