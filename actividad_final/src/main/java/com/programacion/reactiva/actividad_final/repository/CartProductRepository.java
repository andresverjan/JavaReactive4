package com.programacion.reactiva.actividad_final.repository;

import com.programacion.reactiva.actividad_final.model.CartProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartProductRepository extends ReactiveCrudRepository<CartProduct, Integer> {
    Flux<CartProduct> findAllByCartId(int cartId);
    Mono<Void> deleteByCartId(int cartId);
    Mono<Void> deleteByCartIdAndProductId(int cartId, int productId);
}
