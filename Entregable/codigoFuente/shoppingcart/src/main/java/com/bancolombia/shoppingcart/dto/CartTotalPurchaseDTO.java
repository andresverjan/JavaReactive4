package com.bancolombia.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartTotalPurchaseDTO {
    private Long id;
    private double totalPurchase;
}
