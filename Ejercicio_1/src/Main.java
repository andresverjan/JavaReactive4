import java.util.function.Function;

public class Main {

    public static void main(String[] args) {

        Persona persona = new Persona("Jader", "Atehortúa");

        Persona nuevaPersona = procesarPersona(persona,
                Persona -> concatenarNombreApellido(Persona));

        System.out.println(nuevaPersona);

        }

    public static Persona concatenarNombreApellido(Persona persona) {
        String nuevoNombre = persona.getNombre() + " " + persona.getApellido();
        String nuevoApellido = persona.getApellido() + " "+ persona.getNombre();
        return new Persona(nuevoNombre, nuevoApellido);
    }

    public static Persona procesarPersona(Persona persona,
                                          Function<Persona, Persona> funcion) {
        return funcion.apply(persona);
    }

}