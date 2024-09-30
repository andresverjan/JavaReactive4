package com.curso_java.tienda.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("vendedorempresa")

public class VendedorEmpresa {

    @Id
    private String id;  // ID personalizado VE_1, VE_2, etc.
    private String vendedorId;  // Relación con Usuario
    private String empresaId;  // Relación con Empresa

    public VendedorEmpresa(String id, String vendedorId, String empresaId) {
        this.id = id;
        this.vendedorId = vendedorId;
        this.empresaId = empresaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
    }

    public String getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(String empresaId) {
        this.empresaId = empresaId;
    }
}

