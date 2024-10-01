package com.bancolombia.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table("sales_order_master")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrder {
    @Id
    private Long id;

    @Column("subtotalorder")
    private double subtotalorder;

    @Column("totaldiscount")
    private double totaldiscount;

    @Column("totaltax")
    private double totaltax;

    @Column("iscancelled")
    private boolean iscancelled;

    @Column("createdat")
    private LocalDateTime createdAt;

    @Column("updatedat")
    private LocalDateTime updatedAt;

    @Column("userid")
    private Long userid;

    @Transient
    List<SalesOrderDetail> salesOrderDetails;

    public List<SalesOrderDetail> getSalesOrderDetails(){
        return  salesOrderDetails == null ? new ArrayList<>() : salesOrderDetails;
    }
}
