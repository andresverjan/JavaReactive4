package com.programacion.reactiva.trabajo_final.repository;

import com.programacion.reactiva.trabajo_final.model.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {
    Flux<Producto>  findByNameContaining(String name);
}
