package com.angel.react.api.shop.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Table(name = "stock")
public class StockEntity {
    @Id
    private Long id;
    @Column("idproduct")
    private Long idProduct;
    private int quantity;
    private String type;
    private String reference;
    @Column("idsalesorder")
    private Long idSalesOrder;
}
