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
@Table(name = "cart")
public class CartEntity {
    @Id
    private Long id;
    @Column("idproduct")
    private Long idProduct;
    @Column("nameproduct")
    private String nameProduct;
    private int quantity;
    @Column("priceproduct")
    private Float priceProduct;
    @Column("totalpriceproducts")
    private Float totalPriceProducts;
    @Column("totaliva")
    private Float totalIva;
    @Column("totaldiscount")
    private Float totalDiscount;
    private float delivery;
    @Column("idclient")
    private Long idClient;
    @Column("nameclient")
    private String nameClient;
}
