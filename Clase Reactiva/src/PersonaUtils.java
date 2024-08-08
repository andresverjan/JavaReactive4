public interface PersonaUtils {
    public static Persona concatenarNombreApellidos(Persona persona) {
        String nombreCompleto = persona.getNombre() + " " + persona.getApellidos();
        String apellidosCompleto = persona.getApellidos() + " " + persona.getNombre();
        return new Persona(nombreCompleto, apellidosCompleto);
    }
}
