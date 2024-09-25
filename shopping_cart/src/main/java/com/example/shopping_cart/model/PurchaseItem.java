package com.example.shopping_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "purchase_item")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseItem {
    @Id
    private Integer Id;
    @Column("purchase_order_id")
    private Integer purchaseOrderId;
    @Column("product_id")
    private Integer productId;
    @Column("quantity")
    private Integer quantity;
    @Column("price_unit")
    private Float priceUnit;
    @Column("total_item_price")
    private Float totalItemPrice;
    @Column("added_at")
    private LocalDateTime addedAt;
    @Column("update_at")
    private LocalDateTime updateAt;
}
