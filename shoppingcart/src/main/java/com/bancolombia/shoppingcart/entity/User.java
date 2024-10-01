package com.bancolombia.shoppingcart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Long id;
    @Column("username")
    private String username;
    @Column("password")
    private String password;
    @Column("is_seller")
    private boolean is_seller;
    @Column("is_buyer")
    private boolean is_buyer;
    @Column("createdat")
    private LocalDateTime createdAt;
    @Column("updatedat")
    private LocalDateTime updatedAt;
}
