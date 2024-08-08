import java.util.function.Function;

public class Main {
    public static void main(String[] args) {


        Persona persona = new Persona("Paola","Giraldo");

        Persona personaConcat = procesarNombres(persona, Main::concatenar);

        System.out.println("Nombre: " + personaConcat.getNombre());
        System.out.println("Apellido: " + personaConcat.getApellido());

    }


    public static Persona concatenar(Persona persona){

        String nombreApellido = persona.getNombre() + persona.getApellido();
        String apellidoNombre = persona.getApellido() + persona.getNombre();

        return new Persona(nombreApellido, apellidoNombre);

    }

    public static Persona procesarNombres(Persona persona, Function<Persona, Persona> function){
        return function.apply(persona);
    }


}