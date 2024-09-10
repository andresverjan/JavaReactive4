package org.actividad_5;

class Module {
    public String getName() {
        return name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Module(String name, String descripcion) {
        this.name = name;
        this.descripcion = descripcion;
    }

    private String name;

    private String descripcion;

}
