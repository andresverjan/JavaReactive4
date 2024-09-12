package org.example.refuerzos;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "\n    Module{" +
                "name='" + name + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                '}';
    }
}