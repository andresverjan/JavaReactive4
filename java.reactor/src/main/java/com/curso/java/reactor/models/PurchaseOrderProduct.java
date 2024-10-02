package com.curso.java.reactor.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("commerce.purchase_order_product")
@Builder(toBuilder = true)
public class PurchaseOrderProduct {
    @Id
    private int id;
    private int purchaseOrderId;
    private int productId;
    private int quantity;
}
