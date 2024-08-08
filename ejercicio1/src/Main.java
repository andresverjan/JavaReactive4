import persona.Persona;

import java.util.function.Function;

public class Main {

    private static String concatenarNombreApellido(Persona persona) {
        return persona.getNombre() + " " + persona.getApellido();
    }

    private static String aplicarConcatenar(Persona persona,
                                            Function<Persona, String> function) {
        return function.apply(persona);
    }

    public static void main(String[] args) {
        System.out.println(aplicarConcatenar(new Persona("Jhon", "Doe"), Main::concatenarNombreApellido));
    }

}