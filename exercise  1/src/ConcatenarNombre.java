import java.util.function.Function;

public class ConcatenarNombre {

    // Clase Persona con atributos nombre y apellido
    static class Persona {
        private String nombre;
        private String apellido;

        public Persona(String nombre, String apellido) {
            this.nombre = nombre;
            this.apellido = apellido;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }

        @Override
        public String toString() {
            return "Persona {nombre='" + nombre + "', apellido='" + apellido + "'}";
        }
    }

    // Función pura que concatena nombre y apellido
    public static Persona concatenarNombreYApellido(Persona persona) {
        String nuevoNombre = persona.getNombre() + " " + persona.getApellido();
        String nuevoApellido = persona.getApellido() + " " + persona.getNombre();
        return new Persona(nuevoNombre, nuevoApellido);
    }

    // Función de orden superior que procesa la función pura sobre un objeto Persona
    public static Persona procesarPersona(Function<Persona, Persona> funcionPura, Persona persona) {
        return funcionPura.apply(persona);
    }

    public static void main(String[] args) {
        // Crear un objeto Persona
        Persona persona = new Persona("Juan", "Pérez");

        // Procesar la persona usando la función de orden superior y la función pura
        Persona personaProcesada = procesarPersona(ConcatenarNombre::concatenarNombreYApellido, persona);
        System.out.println(personaProcesada);

    }
}