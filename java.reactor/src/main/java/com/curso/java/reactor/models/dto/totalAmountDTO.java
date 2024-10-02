package com.curso.java.reactor.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class totalAmountDTO {
    private double total;
    private double subtotal;
    private double taxes;
    private double shipmentCost;
}
