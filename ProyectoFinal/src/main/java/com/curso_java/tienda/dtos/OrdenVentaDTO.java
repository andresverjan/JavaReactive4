package com.curso_java.tienda.dtos;

import java.math.BigDecimal;

public class OrdenVentaDTO {
    private String id;
    private String usuarioId;
    private BigDecimal total;
    private String estado;

    public OrdenVentaDTO(String id, String usuarioId, BigDecimal total, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.total = total;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
