package com.angel.react.api.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
public class PurchaseOrderFull {
    @JsonProperty("purchaseOrderInfo")
    private PurchaseOrderEntity purchaseOrderEntity;
    @JsonProperty("purchaseOrderDetail")
    private List<PurchaseOrderDetailEntity> purchaseOrderDetailEntity;

    public PurchaseOrderFull(PurchaseOrderEntity purchaseOrderEntity, List<PurchaseOrderDetailEntity> purchaseOrderDetailEntity) {
        this.purchaseOrderEntity = purchaseOrderEntity;
        this.purchaseOrderDetailEntity = purchaseOrderDetailEntity;
    }
}
