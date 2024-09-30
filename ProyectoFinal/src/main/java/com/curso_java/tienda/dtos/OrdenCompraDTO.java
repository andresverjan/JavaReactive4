package com.curso_java.tienda.dtos;

import java.math.BigDecimal;

public class OrdenCompraDTO {
    private String id;
    private String empresaId;
    private String vendedorId;
    private BigDecimal total;

    public OrdenCompraDTO(String id, String empresaId, String vendedorId, BigDecimal total) {
        this.id = id;
        this.empresaId = empresaId;
        this.vendedorId = vendedorId;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
