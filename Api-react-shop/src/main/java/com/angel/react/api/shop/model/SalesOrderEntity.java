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
@Table(name = "sales_order")
public class SalesOrderEntity {
    @Id
    private Long id;
    private String reference;
    private String status;
    @Column("idclient")
    private Long idClient;
    @Column("nameclient")
    private String nameClient;
    private int items;
    @Column("totalbase")
    private Float totalBase;
    @Column("totaliva")
    private Float totalIva;
    @Column("totaldiscount")
    private Float totalDiscount;
    @Column("totalsale")
    private Float totalSale;
    @Column("ivarate")
    private int ivaRate;
    private LocalDate date;
}
