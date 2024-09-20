package com.example.demo.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Setter
@Getter
public class Orders {
    @Id
    private int id;
    @Column("PRODUCTS")
    private String products;
    @Column("CREATED_AT")
    private LocalDateTime createdAt;
    @Column("UPDATE_AT")
    private LocalDateTime updateAt;
    @Column("PRICE")
    private double price;
    @Column("CLIENT_ID")
    private int clientId;

}
