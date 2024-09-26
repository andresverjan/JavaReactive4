package com.example.demo.repository;

import com.example.demo.model.OrdenVenta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrdenVentaRepository extends ReactiveCrudRepository<OrdenVenta, Long> {
    Flux<OrdenVenta> findAllByUserId(Long userId);

    Flux<OrdenVenta> findByFechaOrdenBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}