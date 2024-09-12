package tasks;

class PersonaOne {
    private String nombre;
    private String apellidos;

    public PersonaOne(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    @Override
    public String toString() {
        return "tasks.Persona{nombre='" + nombre + "', apellidos='" + apellidos + "'}";
    }
}
