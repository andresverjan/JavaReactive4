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
public class SalesOrderDetailDTO {
    private Long id;
    private double amount;
    private double salesPrice;
    private double amountdiscount;
    private double amounttax;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long orderid;
    private Long productid;
}
