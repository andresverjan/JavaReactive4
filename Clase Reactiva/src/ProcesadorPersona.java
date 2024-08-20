import java.util.function.Function;

public class ProcesadorPersona {

    public static Persona procesarPersona(Persona persona, Function<Persona, Persona> funcion) {
        return funcion.apply(persona);
    }
}
