package com.example.shopping_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "shopping_cart")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCart {
    @Id
    private Integer Id;
    @Column("client_id")
    private  Integer clientId;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("update_at")
    private LocalDateTime updateAt;
    @Column("status")
    private String status;
    @Column("total_price")
    private Float totalPrice;
}
