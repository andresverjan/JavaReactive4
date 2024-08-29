public class Main {
    public static void main(String[] args) {
        PersonaOne personaOne = new PersonaOne("Juan", "Pérez");

        PersonaOne personaOneProcesada = procesarPersona(personaOne, Main::concatenar);
        System.out.println(personaOneProcesada);
    }

    public static PersonaOne concatenar(PersonaOne personaOne) {
        String nuevoNombre = personaOne.getNombre() + " " + personaOne.getApellidos();
        String nuevoApellidos = personaOne.getApellidos() + " " + personaOne.getNombre();
        return new PersonaOne(nuevoNombre, nuevoApellidos);
    }

    public static PersonaOne procesarPersona(PersonaOne personaOne, java.util.function.Function<PersonaOne, PersonaOne> funcion) {
        return funcion.apply(personaOne);
    }


}