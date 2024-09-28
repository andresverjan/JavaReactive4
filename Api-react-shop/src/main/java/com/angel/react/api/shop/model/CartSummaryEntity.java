package com.angel.react.api.shop.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

@Builder
@Data
public class CartSummaryEntity {
    private Long idClient;
    @Column("nameclient")
    private String nameClient;
    @Column("totalproducts")
    private int totalProducts;
    @Column("totalprice")
    private Float totalPrice;
    private Float delivery;
    private String address;
}
