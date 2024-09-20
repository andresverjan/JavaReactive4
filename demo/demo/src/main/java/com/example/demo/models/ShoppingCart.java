package com.example.demo.models;

import ch.qos.logback.core.net.server.Client;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@Table("shoppingcart")
public class ShoppingCart {
    @Id
    private int id;
    @Column("PRODUCT_ID")
    private int productId;
    @Column("AMOUNT")
    private int amount;
    @Column("PRICE")
    private double price;
    @Column("CLIENT_ID")
    private int clientId;
    @Column("CART_ID")
    private int cartId;
    @Column("CREATED_AT")
    private LocalDateTime createdAt;
    @Column("UPDATE_AT")
    private LocalDateTime updateAt;
}
