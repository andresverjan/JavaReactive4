package com.example.demo.repository;

import com.example.demo.models.ShoppingCart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ShoppingCartRepository extends ReactiveCrudRepository<ShoppingCart, Long> {
    Mono<ShoppingCart> findByCartIdAndProductId(int cartId,int productId);
    Flux<ShoppingCart> findByCartId(int cartId);
    Mono<Void> deleteByCartId(Long cartId);
}
