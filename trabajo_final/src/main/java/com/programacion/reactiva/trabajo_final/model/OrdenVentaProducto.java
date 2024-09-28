package com.programacion.reactiva.trabajo_final.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("ordenventaproducto")
public class OrdenVentaProducto {
    @Id
    private int id;
    private int ordenVentaId;
    private int productoId;
    private int cantidad;
}
