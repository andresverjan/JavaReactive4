package com.example.demo.model;

import com.example.demo.service.ProductoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Table("producto")
public class Producto {
    @Id
    private Long id;

    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Producto(Long id, String name,double price, String description, String imageUrl, int stock, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
