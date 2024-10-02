package com.curso.java.reactor.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("commerce.cart_product")
public class CartProduct {
    @Id
    private int id;
    private int quantity;
    private int cartId;
    private  int productId;
}
