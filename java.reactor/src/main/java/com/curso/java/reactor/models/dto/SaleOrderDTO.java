package com.curso.java.reactor.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SaleOrderDTO {
    private Long saleOrderId;
    private String state;
    private PurchaseCartDTO purchaseCart;
    private totalAmountDTO totalAmount;
}
