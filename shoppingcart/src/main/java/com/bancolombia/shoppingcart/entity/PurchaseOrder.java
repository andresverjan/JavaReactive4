package com.bancolombia.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table("purchase_order_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {
    @Id
    private Long id;

    @Column("subtotalorder")
    private double subtotalorder;

    @Column("iscancelled")
    private boolean iscancelled;

    @Column("createdat")
    private LocalDateTime createdAt;

    @Column("updatedat")
    private LocalDateTime updatedAt;

    @Column("userid")
    private Long userid;

    @Transient
    List<PurchaseOrderDetail> purchaseOrderDetails;

    public List<PurchaseOrderDetail> getPurchaseOrderDetails(){
        return  purchaseOrderDetails == null ? new ArrayList<>() : purchaseOrderDetails;
    }
}
