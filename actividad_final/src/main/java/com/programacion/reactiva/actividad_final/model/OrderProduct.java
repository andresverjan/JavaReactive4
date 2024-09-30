package com.programacion.reactiva.actividad_final.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("public.order_product")
public class OrderProduct {
    private int orderId;
    private int productId;
    private int quantity;
    private double price;
    private double subtotal;
}

