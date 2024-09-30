package compras.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@ToString
@Table("orden_compra")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseOrdersEntity {
    @Id
    @Column("id_orden_compra")
    private Integer id;
    @Column("producto")
    private String product;
    @Column("id_producto")
    private Integer productId;
    @Column("cantidad")
    private Double totalAmount;
    @Column("estado")
    private String status;
    @Column("fecha_creacion")
    private LocalDateTime createdAt;
    @Column("fecha_modificacion")
    private LocalDateTime updatedAt;
}
