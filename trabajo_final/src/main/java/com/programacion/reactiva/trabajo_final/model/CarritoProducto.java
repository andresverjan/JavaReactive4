package com.programacion.reactiva.trabajo_final.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("comercio.carrito_producto")
public class CarritoProducto {
    @Id
    private int id;
    private int cantidad;
    private int carritoId;
    private  int productoId;
}
