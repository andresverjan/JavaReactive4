package com.store.shopping.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ShoppingCartDTO {
    Integer id;
    Integer product;
    String name;
    Integer quantity;
    Double Price;
}
