package org.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaleDto {
    private Long ordenId;
    private LocalDateTime createdAt;
    private Integer cantidad;

}