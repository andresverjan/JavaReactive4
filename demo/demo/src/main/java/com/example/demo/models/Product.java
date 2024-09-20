package com.example.demo.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
@Data
@Setter
@Getter
public class Product {
    @Id
    private int id;
    @Column("NAME")
    private String name;
    @Column("PRICE")
    private double price;
    @Column("DESCRIPTION")
    private String description;
    @Column("IMAGE_URL")
    private String imageUrl;
    @Column("STOCK")
    private int stock;
    @Column("CREATED_AT")
    private LocalDateTime createdAt;
    @Column("UPDATE_AT")
    private LocalDateTime updatedAt;
}
