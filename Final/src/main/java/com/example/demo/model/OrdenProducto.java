package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@Table("orden_producto")
public class OrdenProducto {
    @Id
    private Long id;  // Generado automáticamente
    private Long ordenId;  // ID de la orden
    private List<Long> productoIds;  // Lista de IDs de productos
    private List<Integer> cantidades;  // Lista de cantidades de cada producto
    private List<Double> precios;  // Lista de precios de cada producto

    public OrdenProducto() {}

    public OrdenProducto(Long ordenId, List<Long> productoIds, List<Integer> cantidades, List<Double> precios) {
        this.ordenId = ordenId;
        this.productoIds = productoIds;
        this.cantidades = cantidades;
        this.precios = precios;
    }
}