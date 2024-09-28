package com.angel.react.api.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SalesOrderFull {
    @JsonProperty("salesOrderInfo")
    private SalesOrderEntity salesOrderEntity;
    @JsonProperty("salesOrderDetail")
    private List<SalesOrderDetailEntity> salesOrderDetailEntities;

    public SalesOrderFull(SalesOrderEntity salesOrderEntity, List<SalesOrderDetailEntity> salesOrderDetailEntities) {
        this.salesOrderEntity = salesOrderEntity;
        this.salesOrderDetailEntities = salesOrderDetailEntities;
    }
}
