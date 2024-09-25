package com.example.shopping_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "sales_orders")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesOrder {
    @Id
    private Integer Id;
    @Column("client_id")
    private Integer clientId;
    @Column("shopping_cart_id")
    private Integer shoppingCartId;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("shipping_address")
    private String shippingAddress;
    @Column("shipping_cost")
    private Float shippingCost;
    @Column("taxes")
    private Float taxes;
    @Column("status")
    private String status;
    @Column("total")
    private Float total;
}
