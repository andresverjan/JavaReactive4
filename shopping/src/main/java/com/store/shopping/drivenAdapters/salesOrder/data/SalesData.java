package com.store.shopping.drivenAdapters.salesOrder.data;

import com.store.shopping.entities.enums.SalesEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Builder(toBuilder = true)
@Table("sales")
public class SalesData {
    @Id
    private Integer id;
    @Column("product")
    private Integer product;
    @Column("client")
    private String client;
    @Column("cartItemId")
    private Integer cartItemId;
    @Column("price")
    private Double price;
    @Column("state")
    private SalesEnum state;
    @Column("created_at")
    private Timestamp created_at;
}
