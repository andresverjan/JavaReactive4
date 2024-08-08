package tarea1;

import java.util.function.Function;

public class ConcatenarNombres {

    public static Persona concatenar(Persona persona){
        String nombreConcatenado = persona.getNombre() + " " + persona.getApellido();
        String apellidoConcatenado = persona.getApellido() + " " + persona.getNombre();
        return new Persona(nombreConcatenado, apellidoConcatenado);
    }

    public static Persona aplicarFuncion(Persona persona, Function<Persona, Persona> funcion){
        return funcion.apply(persona);
    }

    public static void main(String[] args) {
        Persona angel = new Persona("Angel","Vivas");
        Persona nuevaPersona = aplicarFuncion(angel, ConcatenarNombres::concatenar);
        System.out.println("Persona: " + angel);
        System.out.println("Persona nombres concatenados: " + nuevaPersona);
    }
}
