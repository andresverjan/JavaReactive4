
public class Persona {
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
        return "Nombre: " + nombre + " Apellido: " + apellido;
    }

    String nombre;
    String apellido;
}
