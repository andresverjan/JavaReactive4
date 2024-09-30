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
@Table(name = "purchase_order")
public class PurchaseOrderEntity {
    @Id
    private Long id;
    private String reference;
    private String status;
    @Column("idsupplier")
    private int idSupplier;
    @Column("namesupplier")
    private String nameSupplier;
    private int items;
    @Column("totalbase")
    private Float totalBase;
    @Column("totaliva")
    private Float totalIva;
    @Column("totalpurchase")
    private Float totalPurchase;
    @Column("ivarate")
    private int ivaRate;
    private LocalDate date;
}
