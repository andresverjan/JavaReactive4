package com.bancolombia.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("cart_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {
    @Id
    private Long id;
    @Column("amount")
    private double amount;
    @Column("price")
    private double price;
    @Column("amountdiscount")
    private double amountdiscount;
    @Column("amounttax")
    private double amounttax;
    @Column("shippingcost")
    private double shippingcost;
    @Column("createdat")
    private LocalDateTime createdAt;
    @Column("updatedat")
    private LocalDateTime updatedAt;
    @Column("cartid")
    private Long cartid;
    @Column("productid")
    private Long productid;
}
