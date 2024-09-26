package com.angel.react.api.shop.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    private Float total;
    private Float iva;
    @Column("ivarate")
    private int ivaRate;
    @Column("totaliva")
    private Float totalIva;
    private LocalDate date;
}
