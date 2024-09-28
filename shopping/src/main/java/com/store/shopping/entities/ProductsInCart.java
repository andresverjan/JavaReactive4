package com.store.shopping.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ProductsInCart {
    Integer id;
    String name;
    Integer quantity;
    Integer product;
    Double Price;
}
