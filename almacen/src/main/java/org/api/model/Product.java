package org.api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("api.productos")
public class Product {

    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("price")
    private Double price;
    @Column("description")
    private String description;
    @Column("image_url")
    private String imageUrl;
    @Column("stock")
    private Integer stock;

}
