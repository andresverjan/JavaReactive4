package com.curso.java.reactor.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("commerce.sale_order_product")
@Builder(toBuilder = true)
public class SaleOrderProduct {
    @Id
    private int id;
    private int saleOrderId;
    private int productId;
    private int quantity;
}
