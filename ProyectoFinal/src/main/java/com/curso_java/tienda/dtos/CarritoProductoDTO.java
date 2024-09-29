package com.curso_java.tienda.dtos;

public class CarritoProductoDTO {
    private String id;
    private String carritoId;
    private ItemCarritoDTO producto;

    public CarritoProductoDTO(String id, String carritoId, ItemCarritoDTO producto) {
        this.id = id;
        this.carritoId = carritoId;
        this.producto = producto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(String carritoId) {
        this.carritoId = carritoId;
    }

    public ItemCarritoDTO getProducto() {
        return producto;
    }

    public void setProducto(ItemCarritoDTO producto) {
        this.producto = producto;
    }
}