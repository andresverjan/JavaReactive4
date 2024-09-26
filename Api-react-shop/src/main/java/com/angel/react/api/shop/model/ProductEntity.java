package com.angel.react.api.shop.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Table(name = "product")
public class ProductEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private String reference;
    private int stock;
    private Float price;
    @Column("priceiva")
    private Float priceIva;
    private Float iva;
    @Column("ivarate")
    private int ivaRate;
    @Column("imageurl")
    private String imageUrl;
}
