package com.example.shopping_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Table(name = "products")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    private Integer id;
    @Column("name")
    private String name;
    @Column("price")
    private Float price;
    @Column("description")
    private String description;
    @Column("image_url")
    private String imageUrl;
    @Column("stock")
    private Integer stock;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("update_at")
    private LocalDateTime updatedAt;


}
