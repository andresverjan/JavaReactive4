package org.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("api.orden_compra_producto")
public class PurchaseProduct {
    @InsertOnlyProperty
    @JsonIgnore
    private Long ordenId;
    private Long productoId;
    private Integer cantidad;

    public PurchaseProduct(Long ordenId, Long productoId, Integer cantidad) {
        this.ordenId = ordenId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public PurchaseProduct() {

    }
}