package com.bancolombia.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("price")
    private double price;
    @Column("description")
    private String description;
    @Column("imageurl")
    private String imageUrl;
    @Column("stock")
    private double stock;
    @Column("taxpercentage")
    private double taxPercentage;
    @Column("discountpercentage")
    private double discountPercentage;
    @Column("createdat")
    private LocalDateTime createdAt;
    @Column("updatedat")
    private LocalDateTime updatedAt;

}
