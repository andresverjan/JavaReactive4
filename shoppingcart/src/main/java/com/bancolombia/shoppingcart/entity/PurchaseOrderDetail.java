package com.bancolombia.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("purchase_order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDetail {
    @Id
    private Long id;
    @Column("amount")
    private double amount;
    @Column("price")
    private double price;
    @Column("createdat")
    private LocalDateTime createdAt;
    @Column("updatedat")
    private LocalDateTime updatedAt;
    @Column("orderid")
    private Long orderid;
    @Column("productid")
    private Long productid;
}
