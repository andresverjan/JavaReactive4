package com.example.shopping_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="purchase_order")
@ToString
public class PurchaseOrder {
    @Id
    private Integer Id;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("transport_cost")
    private Float transportCost;
    @Column("status")
    private String status;
    @Column("seller")
    private String seller;
    @Column("total")
    private Float total;
    @Column("update_at")
    private LocalDateTime updatedAt;
}
