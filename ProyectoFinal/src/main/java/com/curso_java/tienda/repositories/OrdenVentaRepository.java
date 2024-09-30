package com.curso_java.tienda.repositories;

import com.curso_java.tienda.entities.OrdenVenta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrdenVentaRepository extends ReactiveCrudRepository<OrdenVenta, String> {
    Flux<OrdenVenta> findAllByCreatedAtBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}

