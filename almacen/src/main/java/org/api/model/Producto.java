package org.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("api.productos")
public class Producto {


    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("price")
    private Double price;
    @Column("descrption")
    private String description;
    @Column("image_url")
    private String imageUrl;
    @Column("stock")
    private Integer stock;

}
