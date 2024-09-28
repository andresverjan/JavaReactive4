package com.store.shopping.entities;

import com.store.shopping.entities.enums.ShoppingCartEnum;
import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
public class ShoppingCart {

    private Integer id;
    private String buyer;
    private Integer quantity;
    private ShoppingCartEnum status;
    private Integer product;

}

