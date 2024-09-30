package com.programacion.reactiva.actividad_final.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("public.cart_product")
public class CartProduct {
    @Id
    private int id;
    private int cartId;
    private int productId;
    private int quantity;
    private double subtotal;
}
