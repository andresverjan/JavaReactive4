package com.example.shopping_cart.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemRequest {
    // Getters y setters
    private CartItem cartItem;
    private Integer idClient;

}
