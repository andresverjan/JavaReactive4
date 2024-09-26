package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table("orden_venta")
public class OrdenVenta {
    @Id
    private Long id;
    private Long userId;  // Asociado al usuario que tiene el carrito
    private LocalDateTime fechaOrden;
    private String estado;  // "Creada", "Editada", "Cancelada"
    private double total;
    private List<Long> productoIds;  // Lista de identificadores de productos en la orden
    private List<Integer> cantidades;

    public OrdenVenta() {}

    // Constructor completo con lista de productos
    public OrdenVenta(Long userId, LocalDateTime fechaOrden, String estado, double total, List<Long> productoIds, List<Integer> cantidades) {
        this.userId = userId;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.total = total;
        this.productoIds = productoIds;
        this.cantidades = cantidades;
    }

    // Nuevo constructor sin productoIds
    public OrdenVenta(Long userId, LocalDateTime fechaOrden, String estado, double total) {
        this.userId = userId;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.total = total;
    }
}