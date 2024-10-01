package com.bancolombia.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("sales_order_detail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderDetail {
    @Id
    private Long id;
    @Column("amount")
    private double amount;
    @Column("salesPrice")
    private double salesPrice;
    @Column("amountdiscount")
    private double amountdiscount;
    @Column("amounttax")
    private double amounttax;
    @Column("createdat")
    private LocalDateTime createdAt;
    @Column("updatedat")
    private LocalDateTime updatedAt;
    @Column("orderid")
    private Long orderid;
    @Column("productid")
    private Long productid;
}
