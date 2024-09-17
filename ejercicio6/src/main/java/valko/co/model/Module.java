package valko.co.model;

public class Module {
    private String name;
    private String Descripcion;

    public Module(String name, String descripcion) {
        this.name = name;
        Descripcion = descripcion;
    }

    public String getName() {
        return name;
    }

    public String getDescripcion() {
        return Descripcion;
    }
}
