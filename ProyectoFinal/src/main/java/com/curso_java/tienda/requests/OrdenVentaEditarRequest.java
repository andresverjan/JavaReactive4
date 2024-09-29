package com.curso_java.tienda.requests;

import java.util.List;

public class OrdenVentaEditarRequest {
    private List<OrdenProductoRequest> productos;
    private String estado;

    public OrdenVentaEditarRequest(List<OrdenProductoRequest> productos, String estado) {
        this.productos = productos;
        this.estado = estado;
    }

    public List<OrdenProductoRequest> getProductos() {
        return productos;
    }

    public void setProductos(List<OrdenProductoRequest> productos) {
        this.productos = productos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
