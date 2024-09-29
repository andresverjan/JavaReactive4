package com.curso_java.tienda.requests;

import java.util.List;

public class OrdenVentaRequest {
    private String usuarioId;
    private List<OrdenProductoRequest> productos;

    public OrdenVentaRequest(String usuarioId, List<OrdenProductoRequest> productos) {
        this.usuarioId = usuarioId;
        this.productos = productos;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<OrdenProductoRequest> getProductos() {
        return productos;
    }

    public void setProductos(List<OrdenProductoRequest> productos) {
        this.productos = productos;
    }
}