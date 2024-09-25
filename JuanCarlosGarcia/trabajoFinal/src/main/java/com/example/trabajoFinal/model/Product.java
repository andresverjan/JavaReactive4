package com.example.trabajoFinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("productos")
public class Product {
    @Id
    private int id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
