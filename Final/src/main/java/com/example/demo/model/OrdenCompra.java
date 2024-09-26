package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Table("orden_compra")
public class OrdenCompra {
    @Id
    private Long id;  // ID de la orden (generado automáticamente)
    private LocalDateTime fechaOrden;  // Fecha en que se creó la orden
    private String estado;  // Estado de la orden: Creada, Cancelada, etc.
    private double total;  // Total de la orden


    public OrdenCompra(LocalDateTime fechaOrden, String estado, double total) {
        this.fechaOrden = fechaOrden;
        this.estado = estado;
        this.total = total;
    }

    public OrdenCompra() {
    }
}
