package com.curso_java.tienda.dtos;

public class OrdenCompraProductoDTO {
    private String id;
    private String ordenCompraId;
    private String productoId;
    private int cantidad;

    public OrdenCompraProductoDTO(String id, String ordenCompraId, String productoId, int cantidad) {
        this.id = id;
        this.ordenCompraId = ordenCompraId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public String getOrdenCompraId() {
        return ordenCompraId;
    }

    public String getProductoId() {
        return productoId;
    }

    public int getCantidad() {
        return cantidad;
    }
}
