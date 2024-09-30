package com.programacion.reactiva.actividad_final.repository;

import com.programacion.reactiva.actividad_final.model.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, Long> {
   Mono<Cart> findByUserId(int userId);
}
