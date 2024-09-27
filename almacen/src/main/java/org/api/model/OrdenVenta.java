package org.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table("api.ventas")
@AllArgsConstructor
@NoArgsConstructor
public class OrdenVenta {

    @Id
    private Long id;
    private String estado;

    @ReadOnlyProperty
    private List<OrdenProducto> productos;

}
