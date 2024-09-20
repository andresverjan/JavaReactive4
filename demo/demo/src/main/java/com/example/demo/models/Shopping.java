package com.example.demo.models;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class Shopping {
    @Id
    private int id;
    @Column("PRODUCT_ID")
    private int productId;
    @Column("STOCK")
    private int stock;
    @Column("CREATED_AT")
    private LocalDateTime createdAt;
    @Column("UPDATE_AT")
    private LocalDateTime updateAt;

}
