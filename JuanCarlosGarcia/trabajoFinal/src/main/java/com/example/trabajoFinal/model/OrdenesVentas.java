package com.example.trabajoFinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Data
@Table("ordenventa")
public class OrdenesVentas {
    @Id
    private int id;
    private int productId;
    private int personaId;
    private int cantidad;
    private String estado;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
