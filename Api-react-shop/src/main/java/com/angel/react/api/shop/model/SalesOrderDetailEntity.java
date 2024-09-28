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
@Table(name = "sales_order_detail")
public class SalesOrderDetailEntity {
    @Id
    private Long id;
    @Column("idproduct")
    private Long idProduct;
    @Column("nameproduct")
    private String nameProduct;
    @Column("idsalesorder")
    private Long idSalesOrder;
    private String reference;
    private int quantity;
    private Float price;
    private Float total;
    private Float iva;
    @Column("ivarate")
    private int ivaRate;
    @Column("totaliva")
    private Float totalIva;
}
