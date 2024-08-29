public class Persona {
    private String nombre;
    private String apellido;
    private String[] telefonos;
    private int edad;
    private String signo;

    public Persona(String nombre, String apellido, String[] telefonos, int edad, String signo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefonos = telefonos;
        this.edad = edad;
        this.signo = signo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String[] getTelefonos() {
        return telefonos;
    }

    public void setTelefono(String[] telefono) {
        this.telefonos = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSigno() {
        return signo;
    }

    public void setSigno(String signo) {
        this.signo = signo;
    }
}
