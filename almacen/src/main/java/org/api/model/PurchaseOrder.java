package org.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table("api.ordenes_compras")
public class PurchaseOrder {

    @Id
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String estado;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<PurchaseProduct> productos;
}