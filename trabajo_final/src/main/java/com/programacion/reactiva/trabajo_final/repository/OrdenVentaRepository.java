package com.programacion.reactiva.trabajo_final.repository;

import com.programacion.reactiva.trabajo_final.model.OrdenVenta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrdenVentaRepository extends ReactiveCrudRepository<OrdenVenta, Long> {
    Flux<OrdenVenta> findAllByUpdatedAtBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
