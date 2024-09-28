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
    private int idClient;
    @Column("nameclient")
    private String nameClient;
    private int items;
    private Float total;
    private Float iva;
    @Column("ivarate")
    private int ivaRate;
    @Column("totaliva")
    private Float totalIva;
    private LocalDate date;
}
