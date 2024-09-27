package org.api.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VentaDto {
    private Long ordenId;
    private LocalDateTime createdAt;
    private Integer cantidad;

}