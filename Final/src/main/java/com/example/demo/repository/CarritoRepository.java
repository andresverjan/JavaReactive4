package com.example.demo.repository;

import com.example.demo.model.Carrito;
import com.example.demo.model.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface CarritoRepository extends ReactiveCrudRepository<Carrito, Long> {
    Flux<Carrito> findByUserId(Long userId);  // Buscar productos en el carrito por el userId
    Mono<Void> deleteByUserId(Long userId);

}
