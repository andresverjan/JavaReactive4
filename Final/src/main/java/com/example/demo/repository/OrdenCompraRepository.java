package com.example.demo.repository;

import com.example.demo.model.Carrito;
import com.example.demo.model.OrdenCompra;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrdenCompraRepository extends ReactiveCrudRepository<OrdenCompra, Long> {
    Flux<OrdenCompra> findByFechaOrdenBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
