package org.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("api.venta_producto")
public class OrdenProducto {

    @InsertOnlyProperty
    private Long ordenId;
    private Long productoId;
    private Integer cantidad;

    public OrdenProducto(Long ordenId, Long productoId, Integer cantidad) {
        this.ordenId = ordenId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }
}
