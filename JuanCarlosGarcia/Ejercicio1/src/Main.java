import Persona.Persona;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        Persona persona = new Persona("Juan", "Garcia");
        //Persona personaTransformada = transformarPersona(Main::concatenarNombreApellido, persona);

        Persona personaTransformada = transformarPersona(personaLambda ->
                concatenarNombreApellido(personaLambda), persona);
        System.out.println("Nombre: " + personaTransformada.getNombre());
        System.out.println("Apellido: " + personaTransformada.getApellido());
    }

    public static Persona concatenarNombreApellido(Persona p) {
        p.setNombre(p.getNombre() + " " + p.getApellido());
        p.setApellido(p.getApellido() + " " + p.getNombre());
        return p;
    }

    public static Persona transformarPersona(Function<Persona, Persona> funcion, Persona persona) {
        return funcion.apply(persona);
    }
}