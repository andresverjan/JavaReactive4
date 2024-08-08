package actividad1;

import java.util.function.Function;

public class ConcatenarNombresLambda {
    public static Persona aplicarFuncion(Persona persona, Function<Persona, Persona> funcion){
        return funcion.apply(persona);
    }

    public static void main(String[] args) {
        Persona angel = new Persona("Angel","Vivas");

        Persona nuevaPersona = aplicarFuncion(angel,
                (Persona p) -> { Persona persona = new Persona(
                p.getNombre() + " " + p.getApellido(),
                p.getApellido() + " " + p.getNombre());
                return persona;});

        System.out.println("Resultado: " + nuevaPersona);
    }
}
