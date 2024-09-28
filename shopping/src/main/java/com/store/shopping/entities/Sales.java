package com.store.shopping.entities;

import com.store.shopping.entities.enums.SalesEnum;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder(toBuilder = true)
public class Sales {
    Integer id;
    Integer product;
    String client;
    Integer cartItemId;
    Double price;
    SalesEnum state;
    Timestamp created_at;
}
