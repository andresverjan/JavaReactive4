package compras.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Table("producto")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductEntity {
    @Id
    @Column("id_producto")
    private Integer id;
    @Column("nombre")
    private String name;
    @Column("precio")
    private Integer price;
    @Column("descripcion")
    private String description;
    @Column("stock")
    private Integer stock;
    @Column("fecha_creacion")
    private LocalDateTime createdAt;
    @Column("fecha_modificacion")
    private LocalDateTime updatedAt;
}
