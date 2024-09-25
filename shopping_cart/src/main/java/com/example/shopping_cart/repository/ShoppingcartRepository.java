package com.example.shopping_cart.repository;

import com.example.shopping_cart.model.ShoppingCart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ShoppingcartRepository extends ReactiveCrudRepository<ShoppingCart,Integer> {
    Mono<ShoppingCart> findByClientId(Integer idClient);

    Mono<ShoppingCart> findByClientIdAndStatus(Integer idClient, String status);
}
