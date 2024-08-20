public class Main {

    public static void main(String[] args) {
        Persona persona = new Persona("Juan", "Pérez");
        Persona personaProcesada = ProcesadorPersona.procesarPersona(persona, PersonaUtils::concatenarNombreApellidos);
        System.out.println("Nombre: " + personaProcesada.getNombre());
    }
}
