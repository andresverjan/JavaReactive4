package actividad1;

import java.util.function.Function;

public class ConcatenarNombres {

    public static Persona concatenar(Persona persona){
        String nombreConcatenado = persona.getNombre() + " " + persona.getApellido();
        String apellidoConcatenado = persona.getApellido() + " " + persona.getNombre();
        persona.setNombre(nombreConcatenado);
        persona.setApellido(apellidoConcatenado);
        return persona;
    }

    public static Persona aplicarFuncion(Persona persona, Function<Persona, Persona> funcion){
        return funcion.apply(persona);
    }

    public static void main(String[] args) {
        Persona angel = new Persona("Angel","Vivas");
        Persona nuevaPersona = aplicarFuncion(angel, ConcatenarNombres::concatenar);//con metodo de referencia
        System.out.println("Persona con nombres concatenados: " + nuevaPersona);
    }
}
