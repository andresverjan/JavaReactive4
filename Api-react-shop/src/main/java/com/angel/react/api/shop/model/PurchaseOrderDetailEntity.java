package com.angel.react.api.shop.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Table(name = "purchase_order_detail")
public class PurchaseOrderDetailEntity {
    @Id
    private Long id;
    @Column("idproduct")
    private Long idProduct;
    @Column("nameproduct")
    private String nameProduct;
    @Column("idpurchaseorder")
    private Long idPurchaseOrder;
    private String reference;
    private int quantity;
    private Float cost;
    private Float total;
    private Float iva;
    @Column("ivarate")
    private int ivaRate;
    @Column("totaliva")
    private Float totalIva;
}
