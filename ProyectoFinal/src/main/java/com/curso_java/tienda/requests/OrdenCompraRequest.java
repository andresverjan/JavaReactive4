package com.curso_java.tienda.requests;

import java.util.List;

public class OrdenCompraRequest {

    private String empresaId;
    private String vendedorId;
    private List<OrdenProductoRequest> productos;

    public String getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
    }

    public List<OrdenProductoRequest> getProductos() {
        return productos;
    }

    public void setProductos(List<OrdenProductoRequest> productos) {
        this.productos = productos;
    }
}

