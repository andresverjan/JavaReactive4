package com.programacion.reactiva.trabajo_final.repository;

import com.programacion.reactiva.trabajo_final.model.CarritoProducto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CarritoProductoRepository extends ReactiveCrudRepository<CarritoProducto, Long> {
    Flux<CarritoProducto> findByCarritoId(Long id);
    Mono<CarritoProducto> findByCarritoIdAndProductoId(Long carritoId, Long productoId);
    Mono<Void> deleteByCarritoIdAndProductoId(Long carritoId, Long productoId);
    Mono<Void>deleteByCarritoId(Long carritoId);
}
