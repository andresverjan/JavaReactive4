package com.javacourse.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("TBL_PRODUCT_CAR")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCar extends Auditing{
    @Id
    private Long id;

    @Column("shopping_car_id")
    private ShoppingCar shoppingCar;

    @Column("product_id")
    private Product product;

    @Column("quantity")
    private int quantity;

    @Column("price")
    private double price;
}
