package com.angel.react.api.shop.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

@Builder
@Data
public class ProductTopFiveEntity {
    @Column("idProduct")
    private Long idProduct;
    @Column("nameproduct")
    private String nameProduct;
    private int quantity;
    @Column("totalsale")
    private Float totalSale;
}
