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
@Table(name = "compra")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Shop {

    @Id
    @Column(value = "id")
    private Long id;

    @Column(value = "id_compra")
    private Long idShop;

    @Column(value = "fk_id_producto")
    private Long fkIdProduct;

    @Column(value = "cantidad")
    private Long amount;

    @Column(value = "fk_id_usuario")
    private Long fkIdUser;

    @Column(value = "tipo")
    private String type;

    @Column(value = "fecha_creacion")
    private LocalDateTime createdAt;
}
