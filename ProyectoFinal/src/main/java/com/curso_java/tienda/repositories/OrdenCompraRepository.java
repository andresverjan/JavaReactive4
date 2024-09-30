package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.OrdenCompra;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrdenCompraRepository extends ReactiveCrudRepository<OrdenCompra, String> {
    Flux<OrdenCompra> findAllByCreatedAtBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
