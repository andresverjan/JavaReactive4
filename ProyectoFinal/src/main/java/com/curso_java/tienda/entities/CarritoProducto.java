package com.curso_java.tienda.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("carritoproducto")
public class CarritoProducto {

    @Id
    private String id;  // ID personalizado CP_1, CP_2, etc.
    private String carritoId;  // Relación con CarritoCompra
    private String productoId;  // Relación con Producto
    private int cantidad;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CarritoProducto(String carritoId, String productoId, int cantidad, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.carritoId = carritoId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public String getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(String carritoId) {
        this.carritoId = carritoId;
    }
}
