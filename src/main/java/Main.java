public class Main {
    public static void main(String[] args) {
        Persona persona = new Persona("Juan", "Pérez");

        Persona personaProcesada = procesarPersona(persona, Main::concatenar);
        System.out.println(personaProcesada);
    }

    public static Persona concatenar(Persona persona) {
        String nuevoNombre = persona.getNombre() + " " + persona.getApellidos();
        String nuevoApellidos = persona.getApellidos() + " " + persona.getNombre();
        return new Persona(nuevoNombre, nuevoApellidos);
    }

    public static Persona procesarPersona(Persona persona, java.util.function.Function<Persona, Persona> funcion) {
        return funcion.apply(persona);
    }

}