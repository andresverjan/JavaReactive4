package com.example.trabajoFinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("carritocompra")
public class CarritosCompras {
    @Id
    private int id;
    private int personaId;
    private int productId;
    private String nameProduct;
    private int cantidad;
    private double price;
    private String estado;
}
