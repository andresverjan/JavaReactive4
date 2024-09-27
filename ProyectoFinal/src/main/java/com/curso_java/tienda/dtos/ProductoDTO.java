package com.curso_java.tienda.dtos;

import java.math.BigDecimal;

public class ProductoDTO {

    private String id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private int stock;

    public ProductoDTO() {
    }

    public ProductoDTO(String id, String nombre, BigDecimal precio, String descripcion, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
