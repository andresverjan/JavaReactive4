package com.javacourse.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("TBL_PRODUCT")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("price")
    private double price;
    @Column("description")
    private String description;
    @Column("image_url")
    private String imageUrl;
    @Column("stock")
    private int stock;
    @Column("created_date")
    private LocalDateTime createdDate;
    @Column("updated_date")
    private LocalDateTime updatedDate;
}
