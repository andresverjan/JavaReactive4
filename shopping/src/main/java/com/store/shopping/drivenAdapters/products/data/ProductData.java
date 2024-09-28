package com.store.shopping.drivenAdapters.products.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@Table("products")
public class ProductData {
    @Id
    private Integer id;
    @Column("name")
    private String name;
    @Column("stock")
    private Integer stock;
    @Column("price")
    private Double price;
}
