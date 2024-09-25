package com.example.trabajoFinal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Data
@Table("ordencompra")
public class OrdenesCompra {
    @Id
    private int id;
    private int productId;
    private int cantidad;
    private String estado;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
