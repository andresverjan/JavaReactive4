import persona.Persona;

import java.util.function.Function;

public class Main {

    private static String concatenarNombreApellido(Persona persona) {
        String nuevoNombre = persona.getNombre() + " " + persona.getApellido();
        String nuevoApellido = persona.getApellido() + " " + persona.getNombre();
        return new Persona(nuevoNombre, nuevoApellido).toString();
    }

    private static String aplicarConcatenar(Persona persona,
                                            Function<Persona, String> function) {
        return function.apply(persona);
    }

    public static void main(String[] args) {
        System.out.println(aplicarConcatenar(new Persona("Jhon", "Doe"), Main::concatenarNombreApellido));
    }

}