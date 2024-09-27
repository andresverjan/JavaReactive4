package org.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table("api.ventas")
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrder {

    @Id
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String estado;

    @ReadOnlyProperty
    private List<SaleProduct> productos;

}
