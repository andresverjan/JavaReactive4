package org.example.actividadfinal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "ordenes_venta")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SalesOrder {

    @Id
    @Column(value = "id_orden")
    private Long id;

    @Column(value = "fk_id_usuario")
    private Long fkIdUser;

    @Column(value = "fk_id_compra")
    private Long fkIdShop;

    @Column(value = "fecha")
    private LocalDateTime date;

    @Column(value = "total")
    private Double total;

    @Column(value = "estado")
    private String state;
}
