package com.bancolombia.shoppingcart.dto;

import com.bancolombia.shoppingcart.entity.SalesOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDTO {
    private Long id;
    private double subtotalorder;
    private double totaldiscount;
    private double totaltax;
    private boolean iscancelled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userid;

    List<SalesOrderDetailDTO> salesOrderDetailDTOS;

    public List<SalesOrderDetailDTO> getSalesOrderDetails(){
        return  salesOrderDetailDTOS == null ? new ArrayList<>() : salesOrderDetailDTOS;
    }
}
