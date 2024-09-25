package com.example.shopping_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "clients")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {
    @Id
    private Integer Id;
    @Column("email")
    private String email;
    @Column("name")
    private String name;
    @Column("phone")
    private String phone;

}
