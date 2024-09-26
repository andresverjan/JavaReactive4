package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("carrito")
public class Carrito {
    @Id
    private Long id;

    private Long productoId;  // ID del producto
    private Long userId;  // ID del usuario asociado con este carrito
    private String name;
    private double price;
    private int quantity;

    public Carrito(Long id, Long productoId, Long userId, String name, double price, int quantity) {
        this.id = id;
        this.productoId = productoId;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Carrito() {
    }
}


