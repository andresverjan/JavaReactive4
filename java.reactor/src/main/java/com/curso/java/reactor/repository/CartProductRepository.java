package com.curso.java.reactor.repository;

import com.curso.java.reactor.models.CartProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CartProductRepository extends ReactiveCrudRepository<CartProduct, Long> {
    Flux<CartProduct> findByCartId(Long id);
    Mono<CartProduct> findByCartIdAndProductId(Long cartId, Long productId);
    Mono<Void> deleteByCartIdAndProductId(Long cartId, Long productId);
    Mono<Void> deleteByCartId(Long cartId);
}
