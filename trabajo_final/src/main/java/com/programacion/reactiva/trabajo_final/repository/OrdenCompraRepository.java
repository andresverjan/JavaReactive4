package com.programacion.reactiva.trabajo_final.repository;

import com.programacion.reactiva.trabajo_final.model.OrdenCompra;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrdenCompraRepository extends ReactiveCrudRepository<OrdenCompra, Long> {
    Flux<OrdenCompra> findAllByUpdatedAtBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

}

