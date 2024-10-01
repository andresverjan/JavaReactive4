package com.bancolombia.shoppingcart.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDTO {

    private double amount;
    private double price;
    private double amountdiscount;
    private double amounttax;
    private double shippingcost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
