package org.example.actividadfinal.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

public class Product {

    @Id
    @Column(value = "id_producto")
    private Long id;

    @Column(value = "nombre")
    private String name;

    @Column(value = "precio")
    private Double price;

    @Column(value = "descripcion")
    private String description;

    @Column(value = "imagen_url")
    private String imageUrl;

    @Column(value = "cantidad")
    private Long stock;

    @Column(value = "fk_id_usuario")
    private Long fkIdUser;

    @Column(value = "fecha_creacion")
    private LocalDateTime createdAt;

    @Column(value = "fecha_actualizacion")
    private LocalDateTime updatedAt;
}
