package com.store.shopping.entities;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IShoppingCartRepository {
    Mono<ShoppingCart> addProductToCart(ShoppingCart shoppingCart);
    Mono<ShoppingCart> updateItem(Integer id,ShoppingCart shoppingCart);
    Mono<ShoppingCart> getShoppingCartItem(Integer id);
    Flux<ShoppingCart> getShoppingCartItemsActiveByBuyer(String buyer);
    Flux<ProductsInCart> getProductsInCartByBuyer(String buyer);
}
